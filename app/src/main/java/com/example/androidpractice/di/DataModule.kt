package com.example.androidpractice.di

import com.example.androidpractice.data.CategoriesRepoImpl
import com.example.androidpractice.domain.repo.CategoriesRepo
import dagger.Binds
import dagger.Module

@Module
interface DataModule {

    @Binds
    fun bindCategoriesRepo(repo: CategoriesRepoImpl): CategoriesRepo
}
