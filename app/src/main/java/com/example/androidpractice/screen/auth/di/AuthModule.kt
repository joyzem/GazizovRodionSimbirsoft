package com.example.androidpractice.screen.auth.di

import androidx.lifecycle.ViewModel
import com.example.androidpractice.di.ViewModelKey
import com.example.androidpractice.screen.auth.AuthViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface AuthModule {

    @Binds
    @IntoMap
    @ViewModelKey(AuthViewModel::class)
    fun viewModel(viewModel: AuthViewModel): ViewModel
}
