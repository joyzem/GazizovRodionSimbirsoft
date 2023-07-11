package com.example.androidpractice.screen.search.organizations

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.androidpractice.data.OrganizationsSearchRepoImpl
import com.example.androidpractice.domain.model.SearchResult
import com.example.androidpractice.domain.repo.OrganizationsSearchRepo
import javax.inject.Inject

class OrganizationsSearchViewModel @Inject constructor(
    private val repo: OrganizationsSearchRepo
) : ViewModel() {

    private val _searchResults: MutableLiveData<List<SearchResult>> = MutableLiveData()
    val searchResults: LiveData<List<SearchResult>> = _searchResults

    fun search(query: String) {
        _searchResults.postValue(repo.search(query))
    }
}
