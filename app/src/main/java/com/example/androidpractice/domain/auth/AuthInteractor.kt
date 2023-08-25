package com.example.androidpractice.domain.auth

interface AuthInteractor {
    val minEmailLength: Int
    val minPasswordLength: Int
    suspend fun login(email: String, password: String): String?
}