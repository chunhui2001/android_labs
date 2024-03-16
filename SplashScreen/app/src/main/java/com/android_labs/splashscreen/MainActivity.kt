package com.android_labs.splashscreen

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private lateinit var topAnim: Animation;
    private lateinit var bottomAnim: Animation;

    private lateinit var image: ImageView
    private lateinit var logo: TextView
    private lateinit var slogan: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.decorView.windowInsetsController!!.hide(
                WindowInsets.Type.statusBars()
            )
        } else {
            window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        }

        setContentView(R.layout.activity_main)

        this.topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation)
        this.bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation)

        this.image = findViewById(R.id.imageView)
        this.logo = findViewById(R.id.textView)
        this.slogan = findViewById(R.id.textView2)

        this.image.animation = topAnim
        this.logo.animation = bottomAnim
        this.slogan.animation = bottomAnim

        Handler().postDelayed({
            // new activity
            startActivity(Intent(this, HelloActivity::class.java))
            finish();
        }, 5000)
    }
}