package com.android_labs.moviesapp

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.android_labs.moviesapp.models.Datum
import com.android_labs.moviesapp.models.GenresModel
import com.android_labs.moviesapp.models.ListFilm
import com.google.gson.Gson

class MainActivity : AppCompatActivity(), BestMovieAdapter.Callbacks {

    private var sliderHandler: Handler = Handler()

    private lateinit var viewPager1: ViewPager2

    private lateinit var recyclerViewBestMovies: RecyclerView
    private lateinit var recyclerViewCategories: RecyclerView
    private lateinit var recyclerViewUpcomming: RecyclerView

    private lateinit var progressBarBestMovies: ProgressBar
    private lateinit var progressBarCategories: ProgressBar
    private lateinit var progressBarUpcomming: ProgressBar

    private lateinit var requestQueueBestMovies: RequestQueue
    private lateinit var requestQueueCategories: RequestQueue
    private lateinit var requestQueueUpcomming: RequestQueue

    private lateinit var stringReuqestBestMovies: StringRequest
    private lateinit var stringReuqestCategories: StringRequest
    private lateinit var stringReuqestUpcomming: StringRequest

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.viewPager1 = findViewById(R.id.viewPager1)
        this.viewPager1.adapter = SliderAdapter(loadBanners(), viewPager1)
        this.viewPager1.clipToPadding = false
        this.viewPager1.clipChildren = false
        this.viewPager1.offscreenPageLimit = 3
        this.viewPager1.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        this.viewPager1.setPageTransformer(viewPagerTransformer())
        this.viewPager1.currentItem = 1

        this.viewPager1.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                // 当页面被选中时调用，position 是当前选中的页面的索引
                // 在这里可以执行相应的操作
                super.onPageSelected(position)
                sliderHandler.removeCallbacks(Runnable {
                    viewPager1.currentItem += 1
                })
            }
        })

        this.recyclerViewBestMovies = findViewById(R.id.recyclerViewBestMovies)
        this.recyclerViewCategories = findViewById(R.id.recyclerViewCategories)
        this.recyclerViewUpcomming = findViewById(R.id.recyclerViewUpcomming)

        this.progressBarBestMovies = findViewById(R.id.progressBarBestMovies)
        this.progressBarCategories = findViewById(R.id.progressBar2)
        this.progressBarUpcomming = findViewById(R.id.progressBarUpcomming)

        this.recyclerViewBestMovies.layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
        this.recyclerViewCategories.layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
        this.recyclerViewUpcomming.layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)

        this.sendRequestBestMovies()
        this.sendRequestCategories()
        this.sendRequestUpcomming()
    }

    private fun loadBanners(): MutableList<SliderItemsModel> {
        return mutableListOf(
            SliderItemsModel("wide"),
            SliderItemsModel("wide1"),
            SliderItemsModel("wide3"),
        )
    }

    private fun sendRequestBestMovies() {
        requestQueueBestMovies = Volley.newRequestQueue(this@MainActivity)
        this.progressBarBestMovies.visibility = View.VISIBLE
        this.stringReuqestBestMovies = StringRequest(Request.Method.GET,
            "https://moviesapi.ir/api/v1/movies?page=1",
            { response ->
                var gson = Gson()
                this.progressBarBestMovies.visibility = View.GONE
                var listFilm = gson.fromJson(response, ListFilm::class.java)

                this.recyclerViewBestMovies.adapter = BestMovieAdapter(listFilm, this@MainActivity)
            },
            { error ->
                Log.e("MainActivity-sendRequestBestMovies-Error", "responseError: ${error.message}")
                this.progressBarBestMovies.visibility = View.GONE
            })

        requestQueueBestMovies.add(this.stringReuqestBestMovies)
    }

    private fun sendRequestCategories() {
        requestQueueCategories = Volley.newRequestQueue(this@MainActivity)
        this.progressBarCategories.visibility = View.VISIBLE
        this.stringReuqestCategories = StringRequest(Request.Method.GET,
            "https://moviesapi.ir/api/v1/genres",
            { response ->
                var gson = Gson()
                this.progressBarCategories.visibility = View.GONE
                var listCate = gson.fromJson(response, Array<GenresModel>::class.java)

                this.recyclerViewCategories.adapter = CategoryAdapter(listCate.asList())
            },
            { error ->
                Log.e("MainActivity-sendRequestCategories-Error", "responseError: ${error.message}")
                this.progressBarCategories.visibility = View.GONE
            })

        requestQueueCategories.add(this.stringReuqestCategories)
    }

    private fun sendRequestUpcomming() {
        requestQueueUpcomming = Volley.newRequestQueue(this@MainActivity)
        this.progressBarUpcomming.visibility = View.VISIBLE
        this.stringReuqestUpcomming = StringRequest(Request.Method.GET,
            "https://moviesapi.ir/api/v1/movies?page=3",
            { response ->
                var gson = Gson()
                this.progressBarUpcomming.visibility = View.GONE
                var listFilm = gson.fromJson(response, ListFilm::class.java)

                this.recyclerViewUpcomming.adapter = UpcommingAdapter(listFilm, this@MainActivity)
            },
            { error ->
                Log.e("MainActivity-sendRequestUpcomming-Error", "responseError: ${error.message}")
                this.progressBarUpcomming.visibility = View.GONE
            })

        requestQueueUpcomming.add(this.stringReuqestUpcomming)
    }

    override fun onPause() {
        super.onPause()
        sliderHandler.removeCallbacks(Runnable {
            viewPager1.currentItem += 1
        })
    }

    override fun onResume() {
        super.onResume()
        sliderHandler.postDelayed(Runnable {
            viewPager1.currentItem += 1
        }, 2000)
    }

    private fun viewPagerTransformer(): CompositePageTransformer {
        var transformer = CompositePageTransformer()

        transformer.addTransformer(MarginPageTransformer(40))
        transformer.addTransformer { page, position ->
            var scaleFactor = 0.85f
            var absPosition = 1 - Math.abs(position)
            page.scaleY = scaleFactor + absPosition * 0.15f // 缩放
        }

        return transformer
    }

    override fun onFilmSelected(film: Datum) {
        startActivity(DetailsActivity.newIntent(this, film.id))
    }
}