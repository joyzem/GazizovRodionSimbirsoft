package com.example.androidpractice.screen.search.organizations

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.androidpractice.domain.model.SearchResult
import com.example.androidpractice.domain.repo.EventsRepo
import com.example.androidpractice.ui.BaseViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import javax.inject.Inject

class OrganizationsSearchViewModel @Inject constructor(
    private val repo: EventsRepo
) : BaseViewModel() {

    private val _searchResult: MutableLiveData<SearchResult?> = MutableLiveData()
    val searchResult: LiveData<SearchResult?> = _searchResult

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
}
