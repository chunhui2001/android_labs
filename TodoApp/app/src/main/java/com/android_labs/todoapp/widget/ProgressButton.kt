package com.android_labs.todoapp.widget

import android.content.Context
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ProgressBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import com.android_labs.todoapp.R

class ProgressButton(ctx: Context, view: View) {

    private var cardView: CardView = view.findViewById(R.id.cardView)
    private var constraintLayout: ConstraintLayout = view.findViewById(R.id.constraintLayout)
    private var progressBar: ProgressBar = view.findViewById(R.id.progressBar)
    private var textView: TextView = view.findViewById(R.id.textView)

    private var fade_in: Animation = AnimationUtils.loadAnimation(ctx, R.anim.fade_in)

    fun buttonActivited() {
        progressBar.animation = fade_in
        progressBar.visibility = View.VISIBLE
    }

    fun buttonFinished() {
//        constraintLayout.setBackgroundColor(cardView.resources.getColor(R.color.orange))
        progressBar.visibility = View.INVISIBLE
    }
}