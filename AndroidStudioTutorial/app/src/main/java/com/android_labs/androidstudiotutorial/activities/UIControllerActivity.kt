package com.android_labs.androidstudiotutorial.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import com.android_labs.androidstudiotutorial.ui.theme.SystemBarColorsDemoTheme

class UIControllerActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent{
            SystemBarColorsDemoTheme{
                isSystemInDarkTheme()
            }
        }
    }
}