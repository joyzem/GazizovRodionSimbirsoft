package com.example.androidpractice.screen.help

import com.example.androidpractice.domain.categories.model.Category
import com.example.androidpractice.domain.categories.repo.CategoriesRepo
import com.example.androidpractice.ui.BaseViewModel
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class HelpViewModel @Inject constructor(
    categoriesRepo: CategoriesRepo
) : BaseViewModel() {
    val categories: Observable<List<Category>> = categoriesRepo.fetchCategories()
}
