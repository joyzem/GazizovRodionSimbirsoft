package com.example.androidpractice.di

import com.example.androidpractice.data.CategoriesRepoImpl
import com.example.androidpractice.data.EventsRepoImpl
import com.example.androidpractice.data.EventsSearchRepoImpl
import com.example.androidpractice.data.OrganizationsSearchRepoImpl
import com.example.androidpractice.domain.repo.CategoriesRepo
import com.example.androidpractice.domain.repo.EventsRepo
import com.example.androidpractice.domain.repo.EventsSearchRepo
import com.example.androidpractice.domain.repo.OrganizationsSearchRepo
import dagger.Binds
import dagger.Module

@Module
interface DataModule {

    @Binds
    fun bindCategoriesRepo(repo: CategoriesRepoImpl): CategoriesRepo

    @Binds
    fun bindEventsRepo(repo: EventsRepoImpl): EventsRepo

    @Binds
    fun bindEventsSearchRepo(repo: EventsSearchRepoImpl): EventsSearchRepo

    @Binds
    fun bindOrganizationsSearchRepo(repo: OrganizationsSearchRepoImpl): OrganizationsSearchRepo
}
