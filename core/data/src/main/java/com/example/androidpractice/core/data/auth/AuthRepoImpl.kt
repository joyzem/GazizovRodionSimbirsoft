package com.example.androidpractice.core.data.auth

import com.example.androidpractice.core.domain.auth.AuthRepo
import kotlinx.coroutines.delay
import javax.inject.Inject

internal class AuthRepoImpl @Inject constructor() : AuthRepo {
    override suspend fun login(login: String, password: String): String? {
        delay(1000)
        return "token"
    }
}
