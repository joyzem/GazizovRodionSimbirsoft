package com.example.androidpractice.feature.news.di

import com.example.androidpractice.feature.news.details.di.EventDetailsModule
import com.example.androidpractice.feature.news.filter.di.FiltersModule
import dagger.Module

@Module(includes = [EventDetailsModule::class, NewsModule::class, FiltersModule::class])
interface NewsDiModule
