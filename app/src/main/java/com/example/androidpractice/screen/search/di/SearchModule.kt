package com.example.androidpractice.screen.search.di

import androidx.lifecycle.ViewModel
import com.example.androidpractice.di.ViewModelKey
import com.example.androidpractice.screen.search.events.EventsSearchViewModel
import com.example.androidpractice.screen.search.organizations.OrganizationsSearchViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface SearchModule {

    @Binds
    @IntoMap
    @ViewModelKey(EventsSearchViewModel::class)
    fun eventsSearchViewModel(viewModel: EventsSearchViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(OrganizationsSearchViewModel::class)
    fun organizationsSearchViewModel(viewModel: OrganizationsSearchViewModel): ViewModel
}