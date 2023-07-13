package com.example.androidpractice.di

import com.example.androidpractice.data.EventsApiImpl
import com.example.androidpractice.domain.repo.EventsApi
import dagger.Binds
import dagger.Module

@Module
interface NetworkModule {

    @Binds
    fun bindApi(api: EventsApiImpl): EventsApi
}