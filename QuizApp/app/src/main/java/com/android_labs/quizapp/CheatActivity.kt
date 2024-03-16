package com.android_labs.quizapp

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

const val EXTRA_KEY_ANSWER_SHOWED = "com.android_labs.quizapp::ANSWER_SHOWED"

private const val EXTRA_KEY_REAL_ANSWER = "com.android_labs.quizapp::REAL_ANSWER"

class CheatActivity: AppCompatActivity() {

    private lateinit var fieldRealAnswerTv: TextView
    private lateinit var fieldShowAnswerBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cheat)

        this.fieldRealAnswerTv = findViewById(R.id.field_real_answer_tv)
        this.fieldShowAnswerBtn = findViewById(R.id.field_show_answer_btn)

        val realAnswer = intent.getBooleanExtra(EXTRA_KEY_REAL_ANSWER, false)

        this.fieldShowAnswerBtn.setOnClickListener{_: View ->
            this.fieldRealAnswerTv.setText(if (realAnswer) {
                R.string.btn_true_text
            } else {
                R.string.btn_false_text
            })

            setResult(Activity.RESULT_OK, intent.apply {
                putExtra(EXTRA_KEY_ANSWER_SHOWED, true)
            })
        }
    }

    companion object {
        fun newIntent(context: Context, realAnswer: Boolean): Intent {
            return Intent(context, CheatActivity::class.java).apply {
                putExtra(EXTRA_KEY_REAL_ANSWER, realAnswer)
            }
        }
    }

}