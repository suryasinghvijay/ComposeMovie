package com.compose.movie

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp open class AppController : Application() {

    companion object {
        lateinit var instance: AppController
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}
