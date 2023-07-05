package com.example.androidpractice.screen.news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.example.androidpractice.domain.model.Event
import com.example.androidpractice.domain.repo.CategoriesRepo
import com.example.androidpractice.domain.repo.EventsRepo
import javax.inject.Inject

class NewsViewModel @Inject constructor(
    private val eventsRepo: EventsRepo,
    private val categoriesRepo: CategoriesRepo
) : ViewModel() {

    private val filters = categoriesRepo.appliedFilters.asLiveData(viewModelScope.coroutineContext)

    val events = filters.map { filters ->
        val categories = filters.filter { it.checked }.map {
            it.category
        }
        eventsRepo.getEvents().filter {
            (it.categories intersect categories).isNotEmpty()
        }
    }
}
