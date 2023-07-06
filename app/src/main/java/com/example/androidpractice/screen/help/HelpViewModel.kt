package com.example.androidpractice.screen.help

import androidx.lifecycle.ViewModel
import com.example.androidpractice.domain.repo.CategoriesRepo
import javax.inject.Inject

class HelpViewModel @Inject constructor(
    categoriesRepo: CategoriesRepo
) : ViewModel() {
    val categories = categoriesRepo.categories

    init {
        categoriesRepo.updateCategories()
    }
}
