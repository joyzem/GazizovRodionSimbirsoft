package com.example.androidpractice.di

import android.content.Context
import com.example.androidpractice.MainActivity
import com.example.androidpractice.screen.help.HelpFragment
import com.example.androidpractice.screen.news.NewsFragment
import com.example.androidpractice.screen.news.details.EventDetailsFragment
import com.example.androidpractice.screen.news.filter.FiltersFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [DataModule::class, AssetsModule::class])
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun inject(mainActivity: MainActivity)

    fun inject(fragment: HelpFragment)

    fun inject(fragment: NewsFragment)

    fun inject(fragment: EventDetailsFragment)

    fun inject(fragment: FiltersFragment)
}
