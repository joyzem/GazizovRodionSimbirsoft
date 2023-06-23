package com.example.androidpractice.di

import android.content.Context
import com.example.androidpractice.screen.help.HelpFragment
import com.example.androidpractice.screen.news.NewsFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [DataModule::class, AssetsModule::class, ViewModelModule::class])
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun inject(fragment: HelpFragment)

    fun inject(fragment: NewsFragment)
}
