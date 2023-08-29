package com.example.androidpractice.feature.search

import androidx.lifecycle.ViewModel
import com.example.androidpractice.feature.search.di.DaggerSearchComponent
import com.example.androidpractice.feature.search.di.SearchComponent
import com.example.androidpractice.feature.search.di.SearchDepsProvider

class SearchComponentViewModel : ViewModel() {
    val searchComponent: SearchComponent =
        DaggerSearchComponent.builder().deps(SearchDepsProvider.deps).create()
}
