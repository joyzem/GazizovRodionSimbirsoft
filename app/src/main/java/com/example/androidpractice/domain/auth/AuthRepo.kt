package com.example.androidpractice.domain.auth

interface AuthRepo {
    suspend fun login(login: String, password: String): String?
}