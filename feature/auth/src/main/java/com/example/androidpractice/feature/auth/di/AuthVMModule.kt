package com.example.androidpractice.feature.auth.di

import androidx.lifecycle.ViewModel
import com.example.androidpractice.core.di.ViewModelKey
import com.example.androidpractice.feature.auth.AuthViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface AuthVMModule {

    @Binds
    @AuthFeatureScope
    @IntoMap
    @ViewModelKey(AuthViewModel::class)
    fun viewModel(viewModel: AuthViewModel): ViewModel
}
