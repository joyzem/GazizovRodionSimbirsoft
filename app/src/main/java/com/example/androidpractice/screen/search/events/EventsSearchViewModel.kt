package com.example.androidpractice.screen.search.events

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.androidpractice.domain.model.SearchResult
import com.example.androidpractice.domain.repo.EventsRepo
import com.example.androidpractice.ui.BaseViewModel
import javax.inject.Inject

class EventsSearchViewModel @Inject constructor(
    private val repo: EventsRepo
) : BaseViewModel() {

    private val _searchResult: MutableLiveData<SearchResult?> = MutableLiveData(null)
    val searchResult: LiveData<SearchResult?> = _searchResult

    fun search(query: String) {
        compositeDisposable.clear()
        if (query.isBlank()) {
            _searchResult.postValue(null)
            return
        }
        compositeDisposable.add(repo.searchEventsByName(query)
            .subscribe { result ->
                _searchResult.postValue(result)
            }
        )
    }
}
