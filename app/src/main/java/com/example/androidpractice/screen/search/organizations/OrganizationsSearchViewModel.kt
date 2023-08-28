package com.example.androidpractice.screen.search.organizations

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.androidpractice.core.domain.search.repo.SearchRepo
import com.example.androidpractice.core.model.search.SearchResult
import com.example.androidpractice.core.ui.BaseViewModel
import com.example.utils.concurrent.getLoggingCoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "OrganizationsSearchViewModel"

class OrganizationsSearchViewModel @Inject constructor(
    private val repo: SearchRepo
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
            _searchResult.postValue(repo.searchEventsByOrganizationName(query))
        }
    }
}
