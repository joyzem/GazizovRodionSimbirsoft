package com.example.androidpractice.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.androidpractice.screen.help.HelpViewModel
import com.example.androidpractice.screen.news.NewsViewModel
import com.example.androidpractice.screen.news.filter.FiltersViewModel
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

@Singleton
class ViewModelFactory @Inject constructor(
    helpViewModel: Provider<HelpViewModel>,
    filtersViewModel: Provider<FiltersViewModel>,
    newsViewModel: Provider<NewsViewModel>,
) : ViewModelProvider.Factory {

    private val providers = mapOf<Class<out ViewModel>, Provider<out ViewModel>>(
        HelpViewModel::class.java to helpViewModel,
        FiltersViewModel::class.java to filtersViewModel,
        NewsViewModel::class.java to newsViewModel,
    )

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return providers[modelClass]!!.get() as T
    }
}
