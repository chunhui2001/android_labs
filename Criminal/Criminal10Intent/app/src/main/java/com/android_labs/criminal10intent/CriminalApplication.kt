package com.android_labs.criminal10intent

import android.app.Application

class CriminalApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        CrimeRepository.initialize(this)
    }
}