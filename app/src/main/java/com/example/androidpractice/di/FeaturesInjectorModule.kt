package com.example.androidpractice.di

import com.example.androidpractice.feature.news_details.NewsDetailsFeatureApi
import com.example.androidpractice.feature.news_details_impl.NewsDetailsFeatureApiImpl
import dagger.Binds
import dagger.Module

@Module
interface FeaturesInjectorModule {

    @Binds
    fun newsDetails(implementation: NewsDetailsFeatureApiImpl): NewsDetailsFeatureApi
}