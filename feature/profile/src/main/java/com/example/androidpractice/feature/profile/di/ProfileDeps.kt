package com.example.androidpractice.feature.profile.di

import androidx.lifecycle.ViewModelProvider
import kotlin.properties.Delegates.notNull

interface ProfileDeps {
    val viewModelFactory: ViewModelProvider.Factory
}

internal interface ProfileDepsProvider {
    val deps: ProfileDeps

    companion object : ProfileDepsProvider by ProfileDepsStore
}

object ProfileDepsStore : ProfileDepsProvider {
    override var deps: ProfileDeps by notNull()
}