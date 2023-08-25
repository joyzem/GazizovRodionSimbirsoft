package com.example.androidpractice.data.auth

import com.example.androidpractice.domain.auth.AuthInteractor
import com.example.androidpractice.domain.auth.usecase.LoginUseCase
import javax.inject.Inject

class AuthInteractorImpl @Inject constructor(
    private val loginUseCase: LoginUseCase
) : AuthInteractor {
    override val minEmailLength: Int = 6
    override val minPasswordLength: Int = 6

    override suspend fun login(email: String, password: String): String? {
        return loginUseCase.buildUseCase(LoginUseCase.LoginParams(email, password))
    }
}