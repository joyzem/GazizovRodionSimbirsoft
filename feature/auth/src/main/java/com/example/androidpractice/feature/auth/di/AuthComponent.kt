package com.example.androidpractice.feature.auth.di

import com.example.androidpractice.core.di.FeatureScope
import com.example.androidpractice.feature.auth.AuthFragment
import dagger.Component

@Component(dependencies = [AuthDeps::class])
@AuthFeatureScope
internal interface AuthComponent {

    fun inject(fragment: AuthFragment)

    @Component.Builder
    interface Builder {
        fun deps(deps: AuthDeps): Builder
        fun create(): AuthComponent
    }
}

@FeatureScope
annotation class AuthFeatureScope
