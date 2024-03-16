package com.android_labs.todoapp.application

import android.app.Application
import com.android_labs.todoapp.utils.HttpsTrustManager

class TodoApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        HttpsTrustManager.allowAllSSL()
    }
}