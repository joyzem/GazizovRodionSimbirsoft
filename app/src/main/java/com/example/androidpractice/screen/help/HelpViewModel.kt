package com.example.androidpractice.screen.help

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.androidpractice.domain.categories.model.Category
import com.example.androidpractice.domain.categories.repo.CategoriesRepo
import com.example.androidpractice.ui.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class HelpViewModel @Inject constructor(
    categoriesRepo: CategoriesRepo
) : BaseViewModel() {

    val categories: LiveData<List<Category>> =
        categoriesRepo.categories.asLiveData(viewModelScope.coroutineContext)

    init {
        viewModelScope.launch(Dispatchers.IO) {
            categoriesRepo.fetchCategories()
        }
    }
}
