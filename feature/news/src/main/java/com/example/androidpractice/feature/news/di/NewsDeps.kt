package com.example.androidpractice.feature.news.di

import androidx.lifecycle.ViewModelProvider
import com.example.androidpractice.core.domain.categories.repo.CategoriesRepo
import com.example.androidpractice.core.domain.events.repo.EventsRepo
import com.example.androidpractice.feature.news_details.NewsDetailsFeatureApi
import kotlin.properties.Delegates.notNull

interface NewsDeps {
    val viewModelFactory: ViewModelProvider.Factory
    val eventsRepo: EventsRepo
    val categoriesRepo: CategoriesRepo
    val newsDetailsFeatureApi: NewsDetailsFeatureApi
}

interface NewsDepsProvider {
    val deps: NewsDeps

    companion object : NewsDepsProvider by NewsDepsStore
}

object NewsDepsStore : NewsDepsProvider {
    override var deps: NewsDeps by notNull()
}
