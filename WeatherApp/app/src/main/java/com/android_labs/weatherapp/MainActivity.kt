package com.android_labs.weatherapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager

class MainActivity : AppCompatActivity() {

    private lateinit var listView: RecyclerView
    private lateinit var next7Day: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.next7Day = findViewById(R.id.next7Day)
        this.listView = findViewById(R.id.listView)
        this.listView.layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
        this.listView.adapter = HourlyAdapter(loadData())

        this.next7Day.setOnClickListener {
            startActivity(Intent(this@MainActivity, FutureActivity::class.java))
        }
    }

    private fun loadData(): List<Hourly> {
        return mutableListOf(
            Hourly("9 pm", 28, "cloudy"),
            Hourly("11 pm", 29, "sunny"),
            Hourly("12 pm", 30, "wind"),
            Hourly("1 am", 29, "rainy"),
            Hourly("2 am", 27, "storm"),
        )
    }
}