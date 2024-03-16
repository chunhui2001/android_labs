package com.android_labs.uiloverquizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android_labs.uiloverquizapp.databinding.ActivityScoreBinding

class ScoreActivity : AppCompatActivity() {

    private var score: Int = 0
    private lateinit var binding: ActivityScoreBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScoreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        score = intent.getIntExtra("Score", 0)

        binding.apply {
            scoreTxt.text = score.toString()
            backBtn.setOnClickListener {
                startActivity(Intent(this@ScoreActivity, MainActivity::class.java))
            }
        }
    }
}