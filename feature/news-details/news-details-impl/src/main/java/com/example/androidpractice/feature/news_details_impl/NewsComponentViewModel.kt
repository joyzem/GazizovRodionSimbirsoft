package com.example.androidpractice.feature.news_details_impl

import androidx.lifecycle.ViewModel
import com.example.androidpractice.feature.news_details_impl.di.DaggerNewsDetailsComponent
import com.example.androidpractice.feature.news_details_impl.di.NewsDetailsComponent
import com.example.androidpractice.feature.news_details_impl.di.NewsDetailsDepsProvider

internal class NewsDetailsComponentViewModel : ViewModel() {
    val newsDetailsComponent: NewsDetailsComponent =
        DaggerNewsDetailsComponent.builder().deps(NewsDetailsDepsProvider.deps).create()
}