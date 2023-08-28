package com.example.androidpractice.core.domain.usecase

interface UseCase<Params, Result> {

    suspend fun buildUseCase(params: Params): Result
}
