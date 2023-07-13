package com.example.androidpractice.screen.auth.di

import com.example.androidpractice.screen.auth.AuthFragment
import com.example.androidpractice.screen.news.di.NewsModule
import dagger.Subcomponent

@Subcomponent(modules = [AuthModule::class])
interface AuthComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): AuthComponent
    }

    fun inject(fragment: AuthFragment)
}