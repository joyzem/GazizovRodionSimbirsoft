package com.example.androidpractice.feature.news.filter

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.androidpractice.core.domain.categories.repo.CategoriesRepo
import com.example.androidpractice.core.model.category.Category
import com.example.androidpractice.core.model.category.CategoryFilter
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FiltersViewModelTest {

    private val categoriesRepo: CategoriesRepo = mockk()
    private lateinit var viewModel: FiltersViewModel

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        every { categoriesRepo.appliedFilters } returns MutableStateFlow(mockCategoryFilters)
        every { categoriesRepo.setFilters(any()) } answers {}
        viewModel = FiltersViewModel(categoriesRepo)
    }

    @Test
    fun `all filters applied returns all filters checked`() {
        val observer = Observer<List<CategoryFilter>> {}
        viewModel.filters.observeForever(observer)
        assertEquals(mockCategoryFilters.size, viewModel.filters.value?.size)
        viewModel.filters.removeObserver(observer)
    }

    @Test
    fun `uncheck filter returns all filters checked except it`() {
        val observer = Observer<List<CategoryFilter>> {}
        viewModel.filters.observeForever(observer)
        mockCategoryFilters[0] = mockCategoryFilters[0].copy(checked = false)
        viewModel.onFilterChecked(mockCategoryFilters[0])
        val allChecked = viewModel.filters.value?.filter { it.checked }
        val mockCheckedFilters = mockCategoryFilters.filter { it.checked }
        assertEquals(
            mockCheckedFilters.size,
            allChecked?.size
        )
        viewModel.filters.removeObserver(observer)
    }

    @Test
    fun `calling apply filters invokes repo method with exactly same filters`() {
        val targetFilters = mockCategoryFilters
        targetFilters[0] = targetFilters[0].copy(checked = false)

        val observer = Observer<List<CategoryFilter>> { }
        viewModel.filters.observeForever(observer)
        viewModel.onFilterChecked(mockCategoryFilters[0].copy(checked = false))
        viewModel.onApplyFilters()
        verify { categoriesRepo.setFilters(targetFilters) }

        viewModel.filters.removeObserver(observer)
    }

    companion object {
        private val mockCategoryFilters = mutableListOf(
            CategoryFilter(Category("1", "Category 1", "http://category1.com"), true),
            CategoryFilter(Category("2", "Category 2", "http://category2.com"), true)
        )
    }
}
