package com.android_labs.loadingbutton

import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var progressBtnView: View;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.progressBtnView = findViewById(R.id.progressBtn)

        this.progressBtnView.setOnClickListener {

            var progressButton = ProgressButton(this@MainActivity, progressBtnView)

            progressButton.buttonActivated()

            var handler = Handler()

            handler.postDelayed({
                progressButton.buttonFinished()
            }, 3000)
        }
    }
}