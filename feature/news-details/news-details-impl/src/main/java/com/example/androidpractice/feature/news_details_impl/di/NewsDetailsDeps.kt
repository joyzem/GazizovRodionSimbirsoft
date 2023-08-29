package com.example.androidpractice.feature.news_details_impl.di

import androidx.lifecycle.ViewModelProvider
import com.example.androidpractice.core.domain.events.repo.EventsRepo
import kotlin.properties.Delegates.notNull

interface NewsDetailsDeps {
    val viewModelFactory: ViewModelProvider.Factory
    val eventsRepo: EventsRepo
}

internal interface NewsDetailsDepsProvider {
    val deps: NewsDetailsDeps

    companion object : NewsDetailsDepsProvider by NewsDetailsDepsStore
}

object NewsDetailsDepsStore : NewsDetailsDepsProvider {
    override var deps: NewsDetailsDeps by notNull()
}