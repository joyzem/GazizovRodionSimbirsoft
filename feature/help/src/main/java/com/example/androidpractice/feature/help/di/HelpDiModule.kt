package com.example.androidpractice.feature.help.di

import androidx.lifecycle.ViewModel
import com.example.androidpractice.core.di.ViewModelKey
import com.example.androidpractice.feature.help.HelpViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface HelpDiModule {

    @Binds
    @HelpFeatureScope
    @IntoMap
    @ViewModelKey(HelpViewModel::class)
    fun viewModel(viewModel: HelpViewModel): ViewModel
}
