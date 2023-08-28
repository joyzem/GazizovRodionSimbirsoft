package com.example.androidpractice.core.data.di

import com.example.androidpractice.core.data.auth.AuthRepoImpl
import com.example.androidpractice.core.data.categories.CategoriesRepoImpl
import com.example.androidpractice.core.data.events.EventsRepoImpl
import com.example.androidpractice.core.data.search.SearchRepoImpl
import com.example.androidpractice.core.domain.auth.AuthRepo
import com.example.androidpractice.core.domain.categories.repo.CategoriesRepo
import com.example.androidpractice.core.domain.events.repo.EventsRepo
import com.example.androidpractice.core.domain.search.repo.SearchRepo
import dagger.Binds
import dagger.Module

@Module(includes = [DataModuleImpl::class])
interface DataModule

@Module
internal interface DataModuleImpl {

    @Binds
    fun bindCategories(categoriesRepoImpl: CategoriesRepoImpl): CategoriesRepo

    @Binds
    fun bindEvents(eventsRepoImpl: EventsRepoImpl): EventsRepo

    @Binds
    fun bindSearch(searchRepoImpl: SearchRepoImpl): SearchRepo

    @Binds
    fun bindAuthRepo(authRepoImpl: AuthRepoImpl): AuthRepo
}
