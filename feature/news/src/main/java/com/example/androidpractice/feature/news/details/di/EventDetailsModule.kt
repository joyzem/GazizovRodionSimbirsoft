package com.example.androidpractice.feature.news.details.di

import androidx.lifecycle.ViewModel
import com.example.androidpractice.core.di.ViewModelKey
import com.example.androidpractice.feature.news.details.EventDetailsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface EventDetailsModule {
    @Binds
    @IntoMap
    @ViewModelKey(EventDetailsViewModel::class)
    fun viewModel(viewModel: EventDetailsViewModel): ViewModel
}
