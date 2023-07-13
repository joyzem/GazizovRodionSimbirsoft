package com.example.androidpractice.screen.news.di

import androidx.lifecycle.ViewModel
import com.example.androidpractice.di.ViewModelKey
import com.example.androidpractice.screen.news.NewsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface NewsModule {

    @Binds
    @IntoMap
    @ViewModelKey(NewsViewModel::class)
    fun viewModel(viewModel: NewsViewModel): ViewModel
}
