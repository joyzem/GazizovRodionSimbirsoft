package com.example.androidpractice.di

import com.example.androidpractice.data.auth.AuthInteractorImpl
import com.example.androidpractice.domain.auth.AuthInteractor
import dagger.Binds
import dagger.Module

@Module
interface InteractorModule {

    @Binds
    fun authInteractor(interactorImpl: AuthInteractorImpl): AuthInteractor
}
