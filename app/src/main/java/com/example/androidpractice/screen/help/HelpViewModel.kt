package com.example.androidpractice.screen.help

import com.example.androidpractice.domain.repo.CategoriesRepo
import com.example.androidpractice.ui.BaseViewModel
import javax.inject.Inject

class HelpViewModel @Inject constructor(
    categoriesRepo: CategoriesRepo
) : BaseViewModel() {
    val categories = categoriesRepo.categories

    init {
        categoriesRepo.updateCategories()
    }
}
