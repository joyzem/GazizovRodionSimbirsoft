package com.example.androidpractice.feature.search.di

import androidx.lifecycle.ViewModel
import com.example.androidpractice.core.di.ViewModelKey
import com.example.androidpractice.feature.search.SearchViewModel
import com.example.androidpractice.feature.search.events.EventsSearchViewModel
import com.example.androidpractice.feature.search.organizations.OrganizationsSearchViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface SearchDiModule {

    @Binds
    @IntoMap
    @ViewModelKey(EventsSearchViewModel::class)
    fun eventsSearchViewModel(viewModel: EventsSearchViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(OrganizationsSearchViewModel::class)
    fun organizationsSearchViewModel(viewModel: OrganizationsSearchViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SearchViewModel::class)
    fun searchViewModel(viewModel: SearchViewModel): ViewModel
}
