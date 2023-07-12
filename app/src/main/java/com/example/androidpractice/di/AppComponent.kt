package com.example.androidpractice.di

import android.content.Context
import com.example.androidpractice.MainActivity
import com.example.androidpractice.screen.news.GetEventsService
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [DataModule::class, AssetsModule::class, NetworkModule::class])
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun viewModelsFactory(): ViewModelFactory

    fun inject(mainActivity: MainActivity)

    fun inject(service: GetEventsService)
}
