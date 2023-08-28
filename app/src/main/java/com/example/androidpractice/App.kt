package com.example.androidpractice

import android.app.Application
import com.example.androidpractice.di.AppComponent
import com.example.androidpractice.di.DaggerAppComponent
import com.example.androidpractice.feature.auth.di.AuthDepsStore
import com.example.androidpractice.feature.help.di.HelpDepsStore
import com.example.androidpractice.feature.profile.di.ProfileDepsStore

class App : Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(applicationContext)
    }

    override fun onCreate() {
        super.onCreate()
        AuthDepsStore.deps = appComponent
        HelpDepsStore.deps = appComponent
        ProfileDepsStore.deps = appComponent
    }
}
