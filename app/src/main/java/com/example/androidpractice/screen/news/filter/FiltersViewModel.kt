package com.example.androidpractice.screen.news.filter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.androidpractice.domain.model.CategoryFilter
import com.example.androidpractice.domain.repo.CategoriesRepo
import javax.inject.Inject

class FiltersViewModel @Inject constructor(
    private val categoriesRepo: CategoriesRepo
) : ViewModel() {

    private val _filters = MutableLiveData(categoriesRepo.appliedFilters.value)
    val filters: LiveData<List<CategoryFilter>> = _filters

    fun onApplyFilters() {
        filters.value?.let { categoriesRepo.setFilters(it) }
    }

    fun onFilterChecked(changedFilter: CategoryFilter) {
        _filters.postValue(
            filters.value?.map { filter ->
                if (filter.category.id == changedFilter.category.id) {
                    changedFilter
                } else {
                    filter
                }
            }
        )
    }
}
