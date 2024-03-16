package com.android_labs.todoapp.activities

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.android_labs.todoapp.R

class SplashActivity : AppCompatActivity() {

    private lateinit var splashContainer: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        this.splashContainer = findViewById(R.id.splash_container)

        this.splashContainer.setOnClickListener {
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            finish()
        }
    }
}