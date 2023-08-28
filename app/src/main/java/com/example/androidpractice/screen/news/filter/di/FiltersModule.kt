package com.example.androidpractice.screen.news.filter.di

import androidx.lifecycle.ViewModel
import com.example.androidpractice.core.di.ViewModelKey
import com.example.androidpractice.screen.news.filter.FiltersViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface FiltersModule {

    @Binds
    @IntoMap
    @ViewModelKey(FiltersViewModel::class)
    fun viewModel(viewModel: FiltersViewModel): ViewModel
}
