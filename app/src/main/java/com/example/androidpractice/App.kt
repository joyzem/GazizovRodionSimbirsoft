package com.example.androidpractice

import android.app.Application
import com.example.androidpractice.di.AppComponent
import com.example.androidpractice.di.DaggerAppComponent

class App : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.factory().create(applicationContext)
    }
}
