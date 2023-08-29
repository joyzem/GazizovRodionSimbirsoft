package com.example.androidpractice.core.domain.auth

interface AuthRepo {
    suspend fun login(login: String, password: String): String?
}
