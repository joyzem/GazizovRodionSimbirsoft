package com.example.androidpractice.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.androidpractice.MainViewModel
import com.example.androidpractice.screen.auth.AuthViewModel
import com.example.androidpractice.screen.help.HelpViewModel
import com.example.androidpractice.screen.news.NewsViewModel
import com.example.androidpractice.screen.news.details.EventDetailsViewModel
import com.example.androidpractice.screen.news.filter.FiltersViewModel
import com.example.androidpractice.screen.search.events.EventsSearchViewModel
import com.example.androidpractice.screen.search.organizations.OrganizationsSearchViewModel
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

@Singleton
class ViewModelFactory @Inject constructor(
    helpViewModel: Provider<HelpViewModel>,
    filtersViewModel: Provider<FiltersViewModel>,
    newsViewModel: Provider<NewsViewModel>,
    authViewModel: Provider<AuthViewModel>,
    organizationsSearchViewModel: Provider<OrganizationsSearchViewModel>,
    eventsSearchViewModel: Provider<EventsSearchViewModel>,
    mainViewModel: Provider<MainViewModel>,
    eventDetailsViewModel: Provider<EventDetailsViewModel>
) : ViewModelProvider.Factory {

    private val providers = mapOf(
        HelpViewModel::class.java to helpViewModel,
        FiltersViewModel::class.java to filtersViewModel,
        NewsViewModel::class.java to newsViewModel,
        AuthViewModel::class.java to authViewModel,
        OrganizationsSearchViewModel::class.java to organizationsSearchViewModel,
        EventsSearchViewModel::class.java to eventsSearchViewModel,
        MainViewModel::class.java to mainViewModel,
        EventDetailsViewModel::class.java to eventDetailsViewModel
    )

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return providers[modelClass]!!.get() as T
    }
}
