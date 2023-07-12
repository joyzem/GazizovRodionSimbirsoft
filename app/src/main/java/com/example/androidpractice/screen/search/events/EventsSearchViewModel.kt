package com.example.androidpractice.screen.search.events

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.androidpractice.domain.model.SearchResult
import com.example.androidpractice.domain.repo.EventsRepo
import io.reactivex.rxjava3.disposables.CompositeDisposable
import javax.inject.Inject

class EventsSearchViewModel @Inject constructor(
    private val repo: EventsRepo
) : ViewModel() {

    private val _searchResult: MutableLiveData<SearchResult?> = MutableLiveData(null)
    val searchResult: LiveData<SearchResult?> = _searchResult

    private val compositeDisposable = CompositeDisposable()

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

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}
