package com.android_labs.crime02recyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (supportFragmentManager.findFragmentById(R.id.fragment_container) == null) {
//            supportFragmentManager.beginTransaction().add(R.id.fragment_container, CrimeCreateFragment.newInstance()).commit()
            supportFragmentManager.beginTransaction().add(R.id.fragment_container, CrimeListFragment.newInstance()).commit()
        }
    }
}