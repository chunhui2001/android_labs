package com.android_labs.criminal05navigation

import android.app.Application

class CriminalApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        CrimeRepository.initialize(this)
    }
}