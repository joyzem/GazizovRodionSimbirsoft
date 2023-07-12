package com.example.androidpractice

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.androidpractice.domain.repo.CategoriesRepo
import com.example.androidpractice.domain.repo.EventsRepo
import io.reactivex.rxjava3.disposables.CompositeDisposable
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class MainViewModel @Inject constructor(
    eventsRepo: EventsRepo,
    categoriesRepo: CategoriesRepo
) : ViewModel() {

    private val readEvents = MutableStateFlow<List<String>>(listOf())

    val unreadNewsCounter = combine(
        readEvents,
        categoriesRepo.appliedFilters,
        eventsRepo.events
    ) { readEvents, filters, events ->
        val filteredEvents = events?.filter { event ->
            println(categoriesRepo)
            val checkedCategories = filters?.filter {
                it.checked
            }?.map { it.category } ?: listOf()
            (event.categories intersect checkedCategories.toSet()).isNotEmpty()
        } ?: listOf()
        filteredEvents.size - filteredEvents.filter { it.id in readEvents }.size
    }.asLiveData(viewModelScope.coroutineContext)


    private val compositeDisposable = CompositeDisposable()

    init {
        compositeDisposable.add(
            eventsRepo.readEvents.subscribe { id ->
                readEvents.update { list ->
                    list + id
                }
            }
        )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}
