package com.example.androidpractice.feature.auth

import androidx.lifecycle.ViewModel
import com.example.androidpractice.feature.auth.di.AuthDepsProvider
import com.example.androidpractice.feature.auth.di.DaggerAuthComponent

internal class AuthComponentViewModel : ViewModel() {
    val authComponent = DaggerAuthComponent.builder().deps(AuthDepsProvider.deps).create()
}
