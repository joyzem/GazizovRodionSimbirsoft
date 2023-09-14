package com.example.androidpractice.feature.news_details_impl.money

import androidx.lifecycle.viewModelScope
import com.example.androidpractice.core.ui.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class DonationViewModel @Inject constructor() : BaseViewModel() {

    private val _selectedOption = MutableStateFlow(500)
    val selectedOption = _selectedOption.asStateFlow()

    val isInputValid = selectedOption.map { money ->
        money in MINIMUM_VALUE..MAXIMUM_VALUE
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        selectedOption.value in MINIMUM_VALUE..MAXIMUM_VALUE
    )

    val possibleHelpOptions = MutableStateFlow(listOf(100, 500, 1000, 2000)).asStateFlow()

    fun selectOption(value: Int) {
        _selectedOption.update { value }
    }

    companion object {
        private const val MINIMUM_VALUE = 1
        private const val MAXIMUM_VALUE = 9_999_999
    }
}