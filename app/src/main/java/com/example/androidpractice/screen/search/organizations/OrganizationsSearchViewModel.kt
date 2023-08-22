package com.example.androidpractice.screen.search.organizations

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.androidpractice.domain.model.SearchResult
import com.example.androidpractice.domain.repo.EventsRepo
import com.example.androidpractice.ui.BaseViewModel
import com.example.androidpractice.util.concurrent.getLoggingCoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "OrganizationsSearchViewModel"

class OrganizationsSearchViewModel @Inject constructor(
    private val repo: EventsRepo
) : BaseViewModel() {

    private val _searchResult: MutableLiveData<SearchResult?> = MutableLiveData()
    val searchResult: LiveData<SearchResult?> = _searchResult

    fun search(query: String) {
        val ceh = getLoggingCoroutineExceptionHandler(TAG)
        viewModelScope.launch(ceh + Dispatchers.IO) {
            if (query.isBlank()) {
                _searchResult.postValue(null)
                return@launch
            }
            _searchResult.postValue(repo.searchEventsByOrganization(query))
        }
    }
}
