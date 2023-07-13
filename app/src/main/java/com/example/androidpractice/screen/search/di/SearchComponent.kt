package com.example.androidpractice.screen.search.di

import com.example.androidpractice.screen.search.events.EventsSearchFragment
import com.example.androidpractice.screen.search.organizations.OrganizationsSearchFragment
import dagger.Subcomponent

@Subcomponent(
    modules = [
        SearchModule::class
    ]
)
interface SearchComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): SearchComponent
    }

    fun inject(fragment: EventsSearchFragment)
    fun inject(fragment: OrganizationsSearchFragment)
}