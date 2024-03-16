package com.android_labs.criminal04room

import android.app.Application

class CriminalApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        CrimeRepository.initialized(this)
    }
}