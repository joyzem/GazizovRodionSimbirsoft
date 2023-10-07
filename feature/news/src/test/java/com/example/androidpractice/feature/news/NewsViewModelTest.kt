package com.example.androidpractice.feature.news

import com.example.androidpractice.core.domain.categories.repo.CategoriesRepo
import com.example.androidpractice.core.domain.events.repo.EventsRepo
import com.example.androidpractice.core.model.category.Category
import com.example.androidpractice.core.model.category.CategoryFilter
import com.example.androidpractice.core.model.event.Event
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlinx.datetime.LocalDate
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class NewsViewModelTest {

    private lateinit var viewModel: NewsViewModel
    private val eventsRepo: EventsRepo = mockk()
    private val categoriesRepo: CategoriesRepo = mockk()

    @Before
    fun setup() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        // Default mock
        every { categoriesRepo.appliedFilters } returns MutableStateFlow(mockCategoryFilters)
        every { eventsRepo.events } returns MutableStateFlow(mockEvents)
    }

    @Test
    fun `observing events when all filters applied returns all events`() = runTest {
        viewModel = NewsViewModel(eventsRepo, categoriesRepo)
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.events.collect()
        }
        assertEquals(mockEvents.size, viewModel.events.value?.size)
    }

    @Test
    fun `observing events when non filters applied returns empty events`() = runTest {
        every { categoriesRepo.appliedFilters } returns MutableStateFlow(emptyList())
        viewModel = NewsViewModel(eventsRepo, categoriesRepo)
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.events.collect()
        }
        assertEquals(0, viewModel.events.value?.size)
    }

    @Test
    fun `observing events when one filter applied returns events with only that filter`() = runTest {
            every { categoriesRepo.appliedFilters } returns MutableStateFlow(mockCategoryFilters.filter { it.category.id == "1" })
            viewModel = NewsViewModel(eventsRepo, categoriesRepo)
            backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
                viewModel.events.collect()
            }
            assertEquals(
                mockEvents.filter { it.category == "1" }.size,
                viewModel.events.value?.size
            )
        }

    @After
    fun clear() {
        Dispatchers.resetMain()
    }

    companion object {
        private val mockEvents = listOf(
            Event(
                "",
                "",
                "",
                "",
                "",
                LocalDate(20222, 10, 4),
                LocalDate(20222, 10, 4),
                LocalDate(20222, 10, 4),
                "",
                "",
                "",
                "",
                "",
                listOf(),
                "1"
            ),
            Event(
                "",
                "",
                "",
                "",
                "",
                LocalDate(20222, 10, 4),
                LocalDate(20222, 10, 4),
                LocalDate(20222, 10, 4),
                "",
                "",
                "",
                "",
                "",
                listOf(),
                "2"
            )
        )

        private val mockCategoryFilters = listOf(
            CategoryFilter(Category("1", "Category 1", "http://category1.com"), true),
            CategoryFilter(Category("2", "Category 2", "http://category2.com"), true)
        )
    }
}
