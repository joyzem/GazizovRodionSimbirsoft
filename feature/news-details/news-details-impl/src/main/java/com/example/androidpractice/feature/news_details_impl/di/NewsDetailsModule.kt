package com.example.androidpractice.feature.news_details_impl.di

import androidx.lifecycle.ViewModel
import com.example.androidpractice.core.di.ViewModelKey
import com.example.androidpractice.feature.news_details_impl.NewsDetailsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface NewsDetailsVMModule {
    @Binds
    @IntoMap
    @ViewModelKey(NewsDetailsViewModel::class)
    fun viewModel(viewModel: NewsDetailsViewModel): ViewModel
}
