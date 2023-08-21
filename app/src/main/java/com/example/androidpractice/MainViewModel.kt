package com.example.androidpractice

import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.androidpractice.domain.repo.CategoriesRepo
import com.example.androidpractice.domain.repo.EventsRepo
import com.example.androidpractice.ui.BaseViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class MainViewModel @Inject constructor(
    eventsRepo: EventsRepo,
    categoriesRepo: CategoriesRepo
) : BaseViewModel() {

    private val readEvents = MutableStateFlow<List<String>>(listOf())

    val unreadNewsCounter = combine(
        readEvents,
        categoriesRepo.appliedFilters,
        eventsRepo.events
    ) { readEvents, filters, events ->
        val filteredEvents = events?.filter { event ->
            val checkedCategories = filters?.filter {
                it.checked
            }?.map { it.category } ?: listOf()
            (event.categories intersect checkedCategories.toSet()).isNotEmpty()
        } ?: listOf()
        filteredEvents.size - filteredEvents.filter { it.id in readEvents }.size
    }.asLiveData(viewModelScope.coroutineContext)

    init {
        compositeDisposable.add(
            eventsRepo.readEvents.subscribe { id ->
                readEvents.update { list ->
                    list + id
                }
            }
        )
    }
}
