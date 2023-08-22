package com.example.androidpractice.screen.search

import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.androidpractice.ui.BaseViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class SearchViewModel @Inject constructor() : BaseViewModel() {

    private val _queries: MutableStateFlow<CharSequence> = MutableStateFlow("")

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    val queries = _queries.debounce(500).mapLatest {
        it.toString()
    }.asLiveData(viewModelScope.coroutineContext)

    fun newQuery(query: CharSequence) {
        _queries.update { query }
    }
}