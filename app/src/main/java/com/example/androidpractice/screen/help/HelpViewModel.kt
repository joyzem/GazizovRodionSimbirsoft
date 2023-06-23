package com.example.androidpractice.screen.help

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.androidpractice.domain.repo.CategoriesRepo
import javax.inject.Inject

class HelpViewModel @Inject constructor(
    private val categoriesRepo: CategoriesRepo
) : ViewModel() {
    val categories = liveData {
        emit(
            categoriesRepo.getCategories()
        )
    }
}
