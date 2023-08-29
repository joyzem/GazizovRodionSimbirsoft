package com.example.androidpractice.feature.search.di

import com.example.androidpractice.core.di.FeatureScope
import com.example.androidpractice.feature.search.SearchFragment
import com.example.androidpractice.feature.search.events.EventsSearchFragment
import com.example.androidpractice.feature.search.organizations.OrganizationsSearchFragment
import dagger.Component

@Component(
    dependencies = [SearchDeps::class]
)
@SearchFeature
interface SearchComponent {

    @Component.Builder
    interface Builder {
        fun deps(deps: SearchDeps): Builder
        fun create(): SearchComponent
    }

    fun inject(fragment: EventsSearchFragment)
    fun inject(fragment: OrganizationsSearchFragment)
    fun inject(searchFragment: SearchFragment)
}

@FeatureScope
annotation class SearchFeature
