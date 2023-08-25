package com.example.androidpractice.domain.usecase


interface UseCase<Params, Result> {

    suspend fun buildUseCase(params: Params): Result
}
