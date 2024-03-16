package com.android_labs.styleandthemesmaterialdesign

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Define ActionBar object
        val actionBar: ActionBar? = supportActionBar
        val colorDrawable = ColorDrawable(ContextCompat.getColor(this, R.color.primary_dark))

        actionBar?.setBackgroundDrawable(colorDrawable)
        actionBar?.setTitleColor(Color.WHITE)
    }

    private fun ActionBar.setTitleColor(color: Int) {
        val text = SpannableString(title ?: "")
        text.setSpan(ForegroundColorSpan(color),0,text.length, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
        title = text
    }
}