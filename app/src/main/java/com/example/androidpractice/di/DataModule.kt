package com.example.androidpractice.di

import com.example.androidpractice.data.categories.CategoriesRepoImpl
import com.example.androidpractice.data.events.EventsRepoImpl
import com.example.androidpractice.data.search.SearchRepoImpl
import com.example.androidpractice.domain.categories.repo.CategoriesRepo
import com.example.androidpractice.domain.events.repo.EventsRepo
import com.example.androidpractice.domain.search.repo.SearchRepo
import dagger.Binds
import dagger.Module

@Module
interface DataModule {

    @Binds
    fun bindCategoriesRepo(repo: CategoriesRepoImpl): CategoriesRepo

    @Binds
    fun bindEventsRepo(repo: EventsRepoImpl): EventsRepo

    @Binds
    fun bindSearchRepo(repo: SearchRepoImpl): SearchRepo
}
