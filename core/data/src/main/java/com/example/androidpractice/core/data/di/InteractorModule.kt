package com.example.androidpractice.core.data.di

import com.example.androidpractice.core.data.auth.AuthInteractorImpl
import com.example.androidpractice.core.domain.auth.AuthInteractor
import dagger.Binds
import dagger.Module

@Module
interface InteractorModule {

    @Binds
    fun authInteractor(interactorImpl: AuthInteractorImpl): AuthInteractor
}
