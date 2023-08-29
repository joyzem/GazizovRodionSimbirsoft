package com.example.androidpractice.feature.news.di

import androidx.lifecycle.ViewModel
import com.example.androidpractice.core.di.ViewModelKey
import com.example.androidpractice.feature.news.NewsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface NewsModule {

    @Binds
    @IntoMap
    @NewsFeature
    @ViewModelKey(NewsViewModel::class)
    fun viewModel(viewModel: NewsViewModel): ViewModel
}
