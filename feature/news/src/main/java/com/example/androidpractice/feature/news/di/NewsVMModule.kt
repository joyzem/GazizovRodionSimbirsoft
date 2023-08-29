package com.example.androidpractice.feature.news.di

import com.example.androidpractice.feature.news.filter.di.FiltersModule
import dagger.Module

@Module(includes = [NewsModule::class, FiltersModule::class])
interface NewsVMModule
