package com.android_labs.weatherapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class FutureActivity : AppCompatActivity() {

    private lateinit var futureList: RecyclerView
    private lateinit var backBtn: ConstraintLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_future)

        this.backBtn = findViewById(R.id.backBtun)
        this.futureList = findViewById(R.id.viewList2)

        this.futureList.layoutManager = LinearLayoutManager(this@FutureActivity, LinearLayoutManager.VERTICAL, false)
        this.futureList.adapter = FutureAdapter(loadData())

        this.backBtn.setOnClickListener {
            startActivity(Intent(this@FutureActivity, MainActivity::class.java))
        }
    }

    private fun loadData(): List<Future> {
        return mutableListOf(
            Future("Sat", "storm", "Storm", 25, 10),
            Future("Sun", "cloudy", "Cloudy", 24, 16),
            Future("Mon", "windy", "Windy", 29, 15),
            Future("Tue", "cloudy_sunny", "Cloudy Sunny", 22, 13),
            Future("Wen", "sunny", "Sunny", 28, 11),
            Future("Thu", "rainy", "Rainy", 23, 12),
        )
    }
}