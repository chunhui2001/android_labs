package com.android_labs.foodsapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity(), PopularListAdapter.Callbacks {

    private lateinit var categoriesListView: RecyclerView
    private lateinit var popluarListView: RecyclerView

    private lateinit var viewCartBtn: FloatingActionButton

    override fun onFoodSelected(food: FoodModel) {
        startActivity(DetailActivity.newIntent(this@MainActivity, food))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.categoriesListView = findViewById(R.id.categoriesListView)
        this.popluarListView = findViewById(R.id.popluarListView)
        this.viewCartBtn = findViewById(R.id.viewCartBtn)

        this.categoriesListView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        this.popluarListView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        this.categoriesListView.adapter = CategoryListAdapter(loadCategories())
        this.popluarListView.adapter = PopularListAdapter(loadPopulars(), this@MainActivity)

        this.viewCartBtn.setOnClickListener {
            startActivity(Intent(this@MainActivity, CartActivity::class.java))
        }
    }

    private fun loadCategories(): List<CategoryModel> {
        return mutableListOf(
            CategoryModel("Pizza", "cat_1"),
            CategoryModel("Burger", "cat_2"),
            CategoryModel("Hotdog", "cat_3"),
            CategoryModel("Drink", "cat_4"),
            CategoryModel("Donut", "cat_5"),
        )
    }

    private fun loadPopulars(): List<FoodModel> {
        return mutableListOf(
            FoodModel(
                "Pepperoni pizza",
                "pizza1",
                "slices pepperoni, mozzerella cheese, fresh oregano, ground black pepper, pizza sauce",
                9.76,
                0
            ),
            FoodModel(
                "Cheese Burger",
                "burger",
                "beef, Gouda Cheese, Special Sauce, Lettcue, tomato",
                8.97,
                0
            ),
            FoodModel(
                "Vegetable pizza",
                "pizza3",
                "olive oil, Vegetable oil, pitted kalamata, cherry tomatoes, fresh oregano, basil",
                8.51,
                0
            ),
        )
    }
}