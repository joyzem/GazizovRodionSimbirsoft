package com.example.androidpractice.feature.profile.di

import androidx.lifecycle.ViewModel
import com.example.androidpractice.core.di.ViewModelKey
import com.example.androidpractice.feature.profile.ProfileViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ProfileDiModule {

    @Binds
    @IntoMap
    @ViewModelKey(ProfileViewModel::class)
    @ProfileFeature
    fun viewModel(viewModel: ProfileViewModel): ViewModel
}
