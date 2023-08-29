package com.example.androidpractice.di

import com.example.androidpractice.feature.auth.di.AuthVMModule
import com.example.androidpractice.feature.help.di.HelpVMModule
import com.example.androidpractice.feature.news.di.NewsVMModule
import com.example.androidpractice.feature.news_details_impl.di.NewsDetailsVMModule
import com.example.androidpractice.feature.profile.di.ProfileVMModule
import com.example.androidpractice.feature.search.di.SearchVMModule
import dagger.Module

@Module(includes = [
    AuthVMModule::class,
    HelpVMModule::class,
    SearchVMModule::class,
    ProfileVMModule::class,
    NewsVMModule::class,
    NewsDetailsVMModule::class
])
interface ViewModelsDiModule