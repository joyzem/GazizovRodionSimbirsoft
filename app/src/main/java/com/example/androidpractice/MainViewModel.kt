package com.example.androidpractice

import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.androidpractice.domain.categories.repo.CategoriesRepo
import com.example.androidpractice.domain.events.repo.EventsRepo
import com.example.androidpractice.ui.BaseViewModel
import com.example.androidpractice.util.concurrent.getLoggingCoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "MainViewModel"

class MainViewModel @Inject constructor(
    eventsRepo: EventsRepo,
    categoriesRepo: CategoriesRepo
) : BaseViewModel() {

    private val readEvents = MutableStateFlow<List<String>>(listOf())

    val unreadNewsCounter = eventsRepo.unreadNewsCounter(
        readEvents,
        categoriesRepo.appliedFilters,
        eventsRepo.events
    ).asLiveData(viewModelScope.coroutineContext)

    init {
        val ceh = getLoggingCoroutineExceptionHandler(TAG)
        viewModelScope.launch(ceh + Dispatchers.Main) {
            for (eventId in eventsRepo.readEvents) {
                eventId?.let { id ->
                    readEvents.update { list ->
                        list + id
                    }
                }
            }
        }
    }
}
