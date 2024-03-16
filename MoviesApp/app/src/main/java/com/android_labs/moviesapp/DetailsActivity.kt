package com.android_labs.moviesapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.android_labs.moviesapp.models.FilmDetailModel
import com.android_labs.moviesapp.models.ListFilm
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.google.gson.Gson

class DetailsActivity : AppCompatActivity() {

    companion object {
        fun newIntent(ctx: Context, filmId: Int): Intent {
            return Intent(ctx, DetailsActivity::class.java).apply {
                putExtra("filmId", filmId)
            }
        }
    }

    private lateinit var backBtn: ImageView
    private lateinit var scrollView: NestedScrollView

    private lateinit var requestQueue: RequestQueue
    private lateinit var stringReuqest: StringRequest
    private lateinit var progressBar: ProgressBar

    private lateinit var movieNameTxt: TextView
    private lateinit var movieStarTxt: TextView
    private lateinit var movieTimeTxt: TextView
    private lateinit var movieSummaryTxt: TextView
    private lateinit var movieActorsTxt: TextView

    private lateinit var moviePic: ImageView

    private lateinit var genreView: RecyclerView
    private lateinit var actorsListView: RecyclerView

    private var filmId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        this.scrollView = findViewById(R.id.scrollViewDetails)
        this.backBtn = findViewById(R.id.backBtn)
        this.filmId = intent.getIntExtra("filmId", 0)
        this.progressBar = findViewById(R.id.progressBarDetails)

        this.movieNameTxt = findViewById(R.id.movieNameTxt)
        this.movieStarTxt = findViewById(R.id.movieStarTxt)
        this.movieTimeTxt = findViewById(R.id.movieTimeTxt)
        this.movieSummaryTxt = findViewById(R.id.movieSummaryTxt)
        this.movieActorsTxt = findViewById(R.id.movieActorsTxt)
        this.moviePic = findViewById(R.id.moviePic)

        this.genreView = findViewById(R.id.genreView)
        this.genreView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        this.actorsListView = findViewById(R.id.actorsListView)
        this.actorsListView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        this.backBtn.setOnClickListener {

            finish()
        }

        sendRequest()
    }

    fun sendRequest() {
        this.requestQueue = Volley.newRequestQueue(this@DetailsActivity)
        this.progressBar.visibility = View.VISIBLE

        this.stringReuqest = StringRequest(
            Request.Method.GET,
            "https://moviesapi.ir/api/v1/movies/$filmId",
            { response ->
                var gson = Gson()
                this.progressBar.visibility = View.GONE
                var currFilm = gson.fromJson(response, FilmDetailModel::class.java)

                this.movieNameTxt.text = currFilm.title.orEmpty()
                this.movieStarTxt.text = currFilm.imdbRating.orEmpty()
                this.movieTimeTxt.text = currFilm.runtime.orEmpty()
                this.movieSummaryTxt.text = currFilm.plot.orEmpty()
                this.movieActorsTxt.text = currFilm.actors.orEmpty()

                if (currFilm.poster != null) {
                    setImg(this, currFilm.poster.orEmpty(), this.moviePic)
                }

                this.actorsListView.adapter = ActorListAdapter(currFilm.images.orEmpty())
                this.genreView.adapter = GenreViewAdapter(currFilm.genres.orEmpty())
            },
            { error ->
                Log.e("DetailsActivity-sendRequest-Error", "responseError: ${error.message}")
                this.progressBar.visibility = View.GONE
            })

        requestQueue.add(this.stringReuqest)
    }

    private fun setImg(ctx: Context, url: String, img: ImageView) {
        var requestOptions = RequestOptions()
        requestOptions = requestOptions.transforms(CenterCrop(), RoundedCorners(30))

        Glide.with(ctx)
            .load(url)
            .apply(requestOptions)
            .into(img)
    }
}