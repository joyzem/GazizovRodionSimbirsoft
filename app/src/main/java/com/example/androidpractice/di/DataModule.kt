package com.example.androidpractice.di

import com.example.androidpractice.data.CategoriesRepoImpl
import com.example.androidpractice.data.EventsRepoImpl
import com.example.androidpractice.domain.repo.CategoriesRepo
import com.example.androidpractice.domain.repo.EventsRepo
import dagger.Binds
import dagger.Module

@Module
interface DataModule {

    @Binds
    fun bindCategoriesRepo(repo: CategoriesRepoImpl): CategoriesRepo

    @Binds
    fun bindEventsRepo(repo: EventsRepoImpl): EventsRepo
}
