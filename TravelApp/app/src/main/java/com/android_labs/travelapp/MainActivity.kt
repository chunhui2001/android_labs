package com.android_labs.travelapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var viewPopular: RecyclerView
    private lateinit var viewCategory: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.viewPopular = findViewById(R.id.viewPopular)
        this.viewCategory = findViewById(R.id.viewCategory)

        this.viewPopular.layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
        this.viewCategory.layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)

        this.viewPopular.adapter = PopularAdapter(loadPopulars())
        this.viewCategory.adapter = CategoryAdapter(loadCategories())
    }

    private fun loadCategories(): List<Category> {
        return mutableListOf(
            Category("Beaches", "cat1"),
            Category("Canps", "cat2"),
            Category("Forest", "cat3"),
            Category("Desert", "cat4"),
            Category("Mountain", "cat5"),
        )
    }

    private fun loadPopulars(): List<City> {
        return mutableListOf(
            City("Mar caible, avendia lago", "Miami Beach", 2, true, 4.8, "pic1", true, 1000.0, "This 2 bed /1 bath home boasts an enormous, open-living plan, accented by striking architectural features and high-end finishes. Feel inspired by open sight lines that embrace the outdoors, crowned by stunning coffered ceilings."),
            City("Passo Rolle, TN", "Hawaii Beach", 1980, false, 5.0, "pic2", false, 2500.0, "Passo Rolle, 1980 metres above sea level, connects San Martino di Castrozza with the other valleys in the Dolomites. This mountain pass is home to a small cluster of houses with a few hotels, restaurants, bars and shops at the foot of the magnificent amphitheatre of the Pale di San Martino, the south-western door to the Dolomites, a UNESCO World Heritage Site."),
            City("Mar caible, avendia lago", "Miami Beach", 1, false, 5.0, "pic3", false, 2500.0, "This 2 bed /1 bath home boasts an enormous, open-living plan, accented by striking architectural features and high-end finishes. Feel inspired by open sight lines that embrace the outdoors, crowned by stunning coffered ceilings."),
        )
    }
}