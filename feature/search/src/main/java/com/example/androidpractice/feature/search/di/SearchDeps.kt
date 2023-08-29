package com.example.androidpractice.feature.search.di

import androidx.lifecycle.ViewModelProvider
import kotlin.properties.Delegates.notNull

interface SearchDeps {
    val viewModelFactory: ViewModelProvider.Factory
}

internal interface SearchDepsProvider {
    val deps: SearchDeps

    companion object : SearchDepsProvider by SearchDepsStore
}

object SearchDepsStore : SearchDepsProvider {
    override var deps: SearchDeps by notNull()
}