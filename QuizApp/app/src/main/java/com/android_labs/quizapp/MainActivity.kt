package com.android_labs.quizapp

import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider

private const val REQUEST_CODE_CHEAT = 0

class MainActivity : AppCompatActivity() {

    private lateinit var fieldQuestionContentTv: TextView
    private lateinit var fieldTrueBtn: Button
    private lateinit var fieldFalseBtn: Button
    private lateinit var fieldPrevBtn: Button
    private lateinit var fieldNextBtn: Button
    private lateinit var fieldCheatBtn: Button

    private val quizViewModel: QuizViewModel by lazy {
        ViewModelProvider(this)[QuizViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.fieldQuestionContentTv = findViewById(R.id.field_question_content_tv)
        this.fieldTrueBtn = findViewById(R.id.field_true_btn)
        this.fieldFalseBtn = findViewById(R.id.field_false_btn)
        this.fieldPrevBtn = findViewById(R.id.field_prev_btn)
        this.fieldNextBtn = findViewById(R.id.field_next_btn)
        this.fieldCheatBtn = findViewById(R.id.field_cheat_btn)

        incrementIndex(0)

        this.fieldCheatBtn.setOnClickListener { view: View ->

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                startActivityForResult(
                    CheatActivity.newIntent(
                        this,
                        this.quizViewModel.currentAnswer
                    ),
                    REQUEST_CODE_CHEAT,
                    ActivityOptions.makeClipRevealAnimation(
                        view, 0, 0, view.width, view.height
                    ).toBundle()
                )
            } else {
                startActivityForResult(
                    CheatActivity.newIntent(
                        this,
                        this.quizViewModel.currentAnswer
                    ), REQUEST_CODE_CHEAT
                )
            }
        }

        this.fieldNextBtn.setOnClickListener { _: View ->
            incrementIndex(1)
        }

        this.fieldPrevBtn.setOnClickListener { _: View ->
            incrementIndex(-1)
        }

        this.fieldTrueBtn.setOnClickListener { _: View ->
            checkAnswer(true)
        }

        this.fieldFalseBtn.setOnClickListener { _: View ->
            checkAnswer(false)
        }
    }

    private fun checkAnswer(userAnswer: Boolean) {
        val result = if (userAnswer == this.quizViewModel.currentAnswer) {
            "Correct"
        } else {
            "Incorrect"
        }

        Toast.makeText(
            this, result + if (this.quizViewModel.isCheat) {
                " isCheat"
            } else {
                ""
            }, Toast.LENGTH_SHORT
        ).show()
    }

    private fun incrementIndex(step: Int) {
        this.fieldQuestionContentTv.setText(this.quizViewModel.incrementIndex((step)))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode != RESULT_OK) {
            return
        }

        if (requestCode == REQUEST_CODE_CHEAT) {
            this.quizViewModel.isCheat = true
        }
    }
}