package com.example.androidpractice.screen.help

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.androidpractice.domain.categories.model.Category
import com.example.androidpractice.domain.categories.repo.CategoriesRepo
import com.example.androidpractice.ui.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

class HelpViewModel @Inject constructor(
    categoriesRepo: CategoriesRepo
) : BaseViewModel() {

    private val _categories = MutableLiveData<List<Category>>()
    val categories: LiveData<List<Category>> = _categories

    private val flowCategories: Flow<List<Category>> = categoriesRepo.fetchCategories()

    init {
        viewModelScope.launch {
            flowCategories
                .flowOn(Dispatchers.IO)
                .collectLatest { categories ->
                    _categories.postValue(categories)
                }
        }
    }
}
