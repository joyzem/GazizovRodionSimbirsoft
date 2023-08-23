package com.example.androidpractice.screen.search.events

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.androidpractice.domain.search.model.SearchResult
import com.example.androidpractice.domain.search.repo.SearchRepo
import com.example.androidpractice.ui.BaseViewModel
import com.example.androidpractice.util.concurrent.getLoggingCoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "EventsSearchViewModel"

class EventsSearchViewModel @Inject constructor(
    private val repo: SearchRepo
) : BaseViewModel() {

    private val _searchResult: MutableLiveData<SearchResult?> = MutableLiveData(null)
    val searchResult: LiveData<SearchResult?> = _searchResult

    fun search(query: String) {
        val ceh = getLoggingCoroutineExceptionHandler(TAG)
        viewModelScope.launch(ceh + Dispatchers.IO) {
            if (query.isBlank()) {
                _searchResult.postValue(null)
                return@launch
            }
            _searchResult.postValue(repo.searchEventsByName(query))
        }
    }
}
