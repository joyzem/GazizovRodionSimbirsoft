package com.example.androidpractice.feature.help

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.androidpractice.core.domain.categories.repo.CategoriesRepo
import com.example.androidpractice.core.model.category.Category
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HelpViewModelTest {

    private lateinit var categoriesRepo: CategoriesRepo

    private lateinit var viewModel: HelpViewModel

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private val categories = listOf(
        Category("1", "Дети", "url"),
        Category("2", "Взрослые", "url"),
        Category("3", "Животные", "url"),
    )

    @Before
    fun setup() {
        categoriesRepo = mockk()
        every { categoriesRepo.categories } returns flowOf(
            categories
        )
        coEvery { categoriesRepo.fetchCategories() } returns Unit
        viewModel = HelpViewModel(categoriesRepo)
    }

    @Test
    fun getCategories() {
        val observer = Observer<List<Category>> {}
        viewModel.categories.observeForever(observer)
        assert(viewModel.categories.value == categories)
        viewModel.categories.removeObserver(observer)
    }
}
