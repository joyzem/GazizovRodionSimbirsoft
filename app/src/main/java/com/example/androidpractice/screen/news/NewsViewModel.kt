package com.example.androidpractice.screen.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.androidpractice.domain.model.Event
import com.example.androidpractice.domain.repo.CategoriesRepo
import com.example.androidpractice.domain.repo.EventsRepo
import kotlinx.coroutines.flow.combine
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsViewModel @Inject constructor(
    eventsRepo: EventsRepo,
    categoriesRepo: CategoriesRepo
) : ViewModel() {

    val events: LiveData<List<Event>?> = combine(
        categoriesRepo.appliedFilters,
        eventsRepo.events
    ) { filters, events ->
        val checkedCategories = filters?.filter {
            it.checked
        }?.map { it.category } ?: listOf()
        events?.filter {
            (it.categories intersect checkedCategories.toSet()).isNotEmpty()
        }
    }.asLiveData(viewModelScope.coroutineContext)
}
