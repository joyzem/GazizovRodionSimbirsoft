package com.example.androidpractice.data.auth

import com.example.androidpractice.domain.auth.AuthRepo
import kotlinx.coroutines.delay
import javax.inject.Inject

class AuthRepoImpl @Inject constructor() : AuthRepo {
    override suspend fun login(login: String, password: String): String? {
        delay(1000)
        return "token"
    }
}