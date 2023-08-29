package com.example.androidpractice.feature.news.di

import com.example.androidpractice.core.di.FeatureScope
import com.example.androidpractice.feature.news.GetEventsService
import com.example.androidpractice.feature.news.NewsFragment
import com.example.androidpractice.feature.news.details.EventDetailsFragment
import com.example.androidpractice.feature.news.filter.FiltersFragment
import dagger.Component

@Component(dependencies = [NewsDeps::class])
@NewsFeature
internal interface NewsComponent {

    @Component.Builder
    interface Builder {
        fun deps(deps: NewsDeps): Builder
        fun create(): NewsComponent
    }

    fun inject(fragment: NewsFragment)
    fun inject(fragment: EventDetailsFragment)
    fun inject(fragment: FiltersFragment)
    fun inject(service: GetEventsService)
}

@FeatureScope
annotation class NewsFeature
