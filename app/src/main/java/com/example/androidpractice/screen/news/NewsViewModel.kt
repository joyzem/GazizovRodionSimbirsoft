package com.example.androidpractice.screen.news

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.example.androidpractice.domain.model.Event
import com.example.androidpractice.domain.repo.CategoriesRepo
import com.example.androidpractice.domain.repo.EventsRepo
import com.example.androidpractice.screen.news.filter.CategoryFilter
import javax.inject.Inject

class NewsViewModel @Inject constructor(
    private val eventsRepo: EventsRepo,
    private val categoriesRepo: CategoriesRepo
) : ViewModel() {

    init {
        Log.i("NewsViewModel", "init NewsViewModel")
    }

    private val _filters = MutableLiveData<List<CategoryFilter>>(
        categoriesRepo.getCategories().map { category ->
            CategoryFilter(category, true)
        }
    )
    val filters: LiveData<List<CategoryFilter>> = _filters

    val events = filters.map { filters ->
        val categories = filters.filter { it.checked }.map {
            it.category
        }
        eventsRepo.getEvents().filter {
            (it.categories intersect categories).isNotEmpty()
        }
    }

    fun onFilterChecked(changedFilter: CategoryFilter) {
        _filters.postValue(
            filters.value?.map { filter ->
                if (filter.category.id == changedFilter.category.id) {
                    changedFilter
                } else {
                    filter
                }
            }
        )
    }

    fun getEventById(eventId: String): Event? {
        return events.value?.find {
            it.id == eventId
        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("NewsViewModel", "onCleared")
    }
}