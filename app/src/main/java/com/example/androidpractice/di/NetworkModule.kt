package com.example.androidpractice.di

import com.example.androidpractice.data.categories.CategoriesApiImpl
import com.example.androidpractice.data.events.EventsApiImpl
import com.example.androidpractice.domain.categories.api.CategoriesApi
import com.example.androidpractice.domain.events.api.EventsApi
import dagger.Binds
import dagger.Module

@Module
interface NetworkModule {

    @Binds
    fun bindEventsApi(api: EventsApiImpl): EventsApi

    @Binds
    fun bindCategoriesApi(api: CategoriesApiImpl): CategoriesApi
}
