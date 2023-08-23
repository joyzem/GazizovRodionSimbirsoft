package com.example.androidpractice.di

import com.example.androidpractice.data.categories.network.CategoriesRetrofitApi
import com.example.androidpractice.data.events.network.EventsRetrofitApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
object NetworkModule {

    @Singleton
    @Provides
    fun provideEventsApi(
        retrofit: Retrofit,
    ): EventsRetrofitApi {
        return retrofit.create(EventsRetrofitApi::class.java)
    }

    @Singleton
    @Provides
    fun provideCategoriesApi(
        retrofit: Retrofit,
    ): CategoriesRetrofitApi {
        return retrofit.create(CategoriesRetrofitApi::class.java)
    }
}
