package com.example.androidpractice.screen.help.di

import androidx.lifecycle.ViewModel
import com.example.androidpractice.di.ViewModelKey
import com.example.androidpractice.screen.help.HelpViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface HelpModule {

    @Binds
    @IntoMap
    @ViewModelKey(HelpViewModel::class)
    fun viewModel(viewModel: HelpViewModel): ViewModel
}
