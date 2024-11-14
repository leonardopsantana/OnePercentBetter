package com.onepercentbetter

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class OPBApp : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}
