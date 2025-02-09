package com.example.itis_android_tasks

import android.app.Application
import com.example.itis_android_tasks.di.ServiceLocator

class FilmsApp : Application() {
    override fun onCreate() {
        super.onCreate()
        ServiceLocator.initData(this)
    }
}