package com.example.androidpractice.feature.auth

import com.example.androidpractice.core.ui.BaseViewModel
import javax.inject.Inject

class AuthViewModel @Inject constructor(
    private val authFeature: AuthFeature
) : BaseViewModel() {

    val state = authFeature.state
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
}
