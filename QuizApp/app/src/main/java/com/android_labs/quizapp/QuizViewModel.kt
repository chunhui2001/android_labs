package com.android_labs.quizapp

import androidx.lifecycle.ViewModel

class QuizViewModel: ViewModel() {

    var isCheat: Boolean = false

    private val questionList = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true),
    )

    private var currentIndex = 0

    fun incrementIndex(step: Int): Int {
        this.currentIndex = if (this.currentIndex + step < 0) {
            this.questionList.size - 1
        } else {
            (this.currentIndex + step) % this.questionList.size
        }

        return this.questionList[this.currentIndex].textId
    }

    val currentAnswer: Boolean
        get() = this.questionList[this.currentIndex].answer
}