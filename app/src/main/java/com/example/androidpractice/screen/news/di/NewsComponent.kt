package com.example.androidpractice.screen.news.di

import com.example.androidpractice.screen.news.NewsFragment
import com.example.androidpractice.screen.news.details.EventDetailsFragment
import com.example.androidpractice.screen.news.details.di.EventDetailsModule
import com.example.androidpractice.screen.news.filter.FiltersFragment
import com.example.androidpractice.screen.news.filter.di.FiltersModule
import dagger.Subcomponent

@Subcomponent(modules = [NewsModule::class, EventDetailsModule::class, FiltersModule::class])
interface NewsComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): NewsComponent
    }

    fun inject(fragment: NewsFragment)
    fun inject(fragment: EventDetailsFragment)
    fun inject(fragment: FiltersFragment)
}
