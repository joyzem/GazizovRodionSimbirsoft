package com.example.androidpractice.core.network.di

import com.example.androidpractice.core.network.categories.CategoriesRetrofitService
import com.example.androidpractice.core.network.events.EventsRetrofitService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
object NetworkModule {

    @Singleton
    @Provides
    fun provideEventsApi(
        retrofit: Retrofit
    ): EventsRetrofitService {
        return retrofit.create(EventsRetrofitService::class.java)
    }

    @Singleton
    @Provides
    fun provideCategoriesApi(
        retrofit: Retrofit
    ): CategoriesRetrofitService {
        return retrofit.create(CategoriesRetrofitService::class.java)
    }
}
