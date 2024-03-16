package com.android_labs.criminal06update

import android.app.Application

class CriminalApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        CrimeRepository.initialize(this)
    }
}