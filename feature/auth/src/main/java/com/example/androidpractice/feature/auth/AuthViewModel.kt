package com.example.androidpractice.feature.auth

import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.androidpractice.core.ui.BaseViewModel
import javax.inject.Inject

class AuthViewModel @Inject constructor(
    private val authFeature: AuthFeature
) : BaseViewModel() {

    val state = authFeature.state.asLiveData(viewModelScope.coroutineContext)
    val news = authFeature.news

    fun setEmail(email: String) {
        authFeature.newWish(AuthFeature.AuthWish.EmailInput(email))
    }

    fun setPassword(password: String) {
        authFeature.newWish(AuthFeature.AuthWish.PasswordInput(password))
    }

    fun changePasswordVisibility() {
        authFeature.newWish(AuthFeature.AuthWish.PasswordVisibility)
    }

    fun loginButtonClicked() {
        authFeature.newWish(AuthFeature.AuthWish.LogIn)
    }

    // Other intents
}
