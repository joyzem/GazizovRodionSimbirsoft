package com.example.androidpractice.domain.auth.usecase

import com.example.androidpractice.domain.auth.AuthRepo
import com.example.androidpractice.domain.usecase.UseCase
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val authRepo: AuthRepo) :
    UseCase<LoginUseCase.LoginParams, String?> {

    override suspend fun buildUseCase(params: LoginParams): String? {
        return authRepo.login(params.email, params.password)
    }

    data class LoginParams(
        val email: String,
        val password: String
    )
}