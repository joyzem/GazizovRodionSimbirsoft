package com.example.androidpractice.screen.news.filter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.androidpractice.domain.model.CategoryFilter
import com.example.androidpractice.domain.repo.CategoriesRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class FiltersViewModel @Inject constructor(
    private val categoriesRepo: CategoriesRepo
) : ViewModel() {

    private val appliedFilters = categoriesRepo.appliedFilters

    private val _filters = appliedFilters as MutableStateFlow
    val filters = _filters.asLiveData(viewModelScope.coroutineContext)

    fun onApplyFilters() {
        filters.value?.let { categoriesRepo.setFilters(it) }
    }

    fun onFilterChecked(changedFilter: CategoryFilter) {
        _filters.update {
            filters.value?.map { filter ->
                if (filter.category.id == changedFilter.category.id) {
                    changedFilter
                } else {
                    filter
                }
            }
        }
    }
}
