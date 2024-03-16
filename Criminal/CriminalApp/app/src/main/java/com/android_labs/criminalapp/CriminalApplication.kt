package com.android_labs.criminalapp

import android.app.Application

class CriminalApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        CrimeRepository.initialize(this)
    }
}