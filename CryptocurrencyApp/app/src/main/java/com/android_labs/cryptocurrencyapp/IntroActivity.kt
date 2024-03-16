package com.android_labs.cryptocurrencyapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView

class IntroActivity : AppCompatActivity() {

    private lateinit var goButton: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        this.goButton = findViewById(R.id.go_button)

        this.goButton.setOnClickListener { _: View ->
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}