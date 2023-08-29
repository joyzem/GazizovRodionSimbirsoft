package com.example.androidpractice.feature.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.androidpractice.core.domain.categories.repo.CategoriesRepo
import com.example.androidpractice.core.domain.events.repo.EventsRepo
import com.example.androidpractice.core.model.event.Event
import com.example.androidpractice.core.ui.BaseViewModel
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class NewsViewModel @Inject constructor(
    eventsRepo: EventsRepo,
    categoriesRepo: CategoriesRepo
) : BaseViewModel() {

    val events: LiveData<List<Event>?> = combine(
        categoriesRepo.appliedFilters,
        eventsRepo.events
    ) { filters, events ->
        val checkedCategories = filters?.filter {
            it.checked
        }?.map { it.category } ?: listOf()
        events?.filter { event ->
            event.category in checkedCategories.map { category -> category.id }
        }
    }.asLiveData(viewModelScope.coroutineContext)
}
