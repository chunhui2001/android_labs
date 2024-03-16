package com.android_labs.criminal05navigation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import java.util.UUID

class MainActivity : AppCompatActivity(), CrimeListFragment.Callbacks {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (supportFragmentManager.findFragmentById(R.id.fragment_container) == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, CrimeListFragment.newFragment()).commit()
        }
    }

    override fun onCrimeSelected(crimeId: UUID) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, CrimeCreateFragment.newInstance(crimeId))
            .addToBackStack(null).commit()
    }
}