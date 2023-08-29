package com.example.androidpractice.feature.news.filter

import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.androidpractice.core.domain.categories.repo.CategoriesRepo
import com.example.androidpractice.core.model.category.CategoryFilter
import com.example.androidpractice.core.ui.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class FiltersViewModel @Inject constructor(
    private val categoriesRepo: CategoriesRepo
) : BaseViewModel() {

    private val _filters = MutableStateFlow<List<CategoryFilter>>(listOf())
    val filters = _filters.asLiveData(viewModelScope.coroutineContext)

    init {
        viewModelScope.launch {
            categoriesRepo.appliedFilters.collectLatest { newFilters ->
                _filters.update {
                    newFilters ?: listOf()
                }
            }
        }
    }

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
            } ?: listOf()
        }
    }
}
