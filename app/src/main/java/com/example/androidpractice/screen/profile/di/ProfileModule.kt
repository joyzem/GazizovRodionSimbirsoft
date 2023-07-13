package com.example.androidpractice.screen.profile.di

import androidx.lifecycle.ViewModel
import com.example.androidpractice.di.ViewModelKey
import com.example.androidpractice.screen.profile.ProfileViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ProfileModule {

    @Binds
    @IntoMap
    @ViewModelKey(ProfileViewModel::class)
    fun viewModel(viewModel: ProfileViewModel): ViewModel
}