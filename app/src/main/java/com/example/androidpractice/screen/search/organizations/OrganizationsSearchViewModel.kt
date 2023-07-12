package com.example.androidpractice.screen.search.organizations

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.androidpractice.domain.model.SearchResult
import com.example.androidpractice.domain.repo.EventsRepo
import io.reactivex.rxjava3.disposables.CompositeDisposable
import javax.inject.Inject

class OrganizationsSearchViewModel @Inject constructor(
    private val repo: EventsRepo
) : ViewModel() {

    private val _searchResult: MutableLiveData<SearchResult?> = MutableLiveData()
    val searchResult: LiveData<SearchResult?> = _searchResult

    private val compositeDisposable = CompositeDisposable()

    fun search(query: String) {
        compositeDisposable.clear()
        if (query.isBlank()) {
            _searchResult.postValue(null)
            return
        }
        compositeDisposable.add(
            repo.searchEventsByOrganization(query)
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
