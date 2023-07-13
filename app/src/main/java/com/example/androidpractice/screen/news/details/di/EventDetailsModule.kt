package com.example.androidpractice.screen.news.details.di

import androidx.lifecycle.ViewModel
import com.example.androidpractice.di.ViewModelKey
import com.example.androidpractice.screen.news.details.EventDetailsViewModel
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