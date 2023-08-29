package com.example.androidpractice.feature.news

import androidx.lifecycle.ViewModel
import com.example.androidpractice.feature.news.di.DaggerNewsComponent
import com.example.androidpractice.feature.news.di.NewsComponent
import com.example.androidpractice.feature.news.di.NewsDepsProvider
import com.example.androidpractice.feature.news.di.NewsFeature

@NewsFeature
internal class NewsComponentViewModel : ViewModel() {
    val newsComponent: NewsComponent = DaggerNewsComponent.builder().deps(NewsDepsProvider.deps).create()
}
