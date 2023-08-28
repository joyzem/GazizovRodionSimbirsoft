package com.example.androidpractice.feature.auth.di

import androidx.lifecycle.ViewModelProvider
import com.example.androidpractice.core.domain.auth.AuthInteractor
import kotlin.properties.Delegates.notNull

interface AuthDeps {
    val viewModelFactory: ViewModelProvider.Factory
    val authInteractor: AuthInteractor
}

interface AuthDepsProvider {
    val deps: AuthDeps

    companion object : AuthDepsProvider by AuthDepsStore
}

object AuthDepsStore : AuthDepsProvider {
    override var deps: AuthDeps by notNull()
}
