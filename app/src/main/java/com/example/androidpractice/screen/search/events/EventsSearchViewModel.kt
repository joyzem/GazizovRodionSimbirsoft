package com.example.androidpractice.screen.search.events

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.androidpractice.data.OrganizationsSearchRepoImpl
import com.example.androidpractice.domain.model.SearchResult
import com.example.androidpractice.domain.repo.EventsSearchRepo
import javax.inject.Inject

class EventsSearchViewModel @Inject constructor(
    private val repo: EventsSearchRepo
) : ViewModel() {

    private val _searchResults: MutableLiveData<List<SearchResult>> = MutableLiveData()
    val searchResults: LiveData<List<SearchResult>> = _searchResults

    fun search(query: String) {
        _searchResults.postValue(repo.search(query))
    }
}
