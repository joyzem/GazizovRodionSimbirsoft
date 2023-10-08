package com.example.androidpractice.feature.news_details_impl

import android.provider.CalendarContract.Instances.EVENT_ID
import androidx.core.os.bundleOf
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.example.androidpractice.core.domain.events.repo.EventsRepo
import com.example.androidpractice.core.model.event.Event
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDate
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import com.example.androidpractice.core.designsystem.R as designR

@MediumTest
@RunWith(AndroidJUnit4::class)
class NewsDetailsFragmentTest {

    private val viewModelProvider = object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return NewsDetailsViewModel(eventsRepo) as T
        }
    }

    private val eventsRepo: EventsRepo = mockk {
        coEvery { readEvent(any()) } answers {}
        every { events } returns flowOf(
            listOf(
                Event(
                    id = "1",
                    title = "movet",
                    subtitle = "intellegebat",
                    description = "convallis",
                    sponsor = "a",
                    startDate = LocalDate(2022, 10, 10),
                    endDate = LocalDate(2022, 10, 10),
                    createAt = LocalDate(2022, 10, 10),
                    address = "iudicabit",
                    phoneNumber = "(651) 210-1717",
                    email = "neva.parsons@example.com",
                    siteUrl = "https://www.google.com/#q=reprimique",
                    imagePreview = "tota",
                    imageUrls = listOf(),
                    category = "1"
                )
            )
        )
    }

    @Test
    fun fragment_contains_event_title() = runTest {
        launchFragment("1")
        onView(withId(R.id.eventTitleTextView))
            .check(matches(withText(eventsRepo.events.single()?.first()?.title)))
    }

    // FIXME: This test fails because IllegalStateException is thrown and it can't be handled by test
    @Test(expected = IllegalStateException::class)
    fun illegal_event_id_throws_exception() {
        launchFragment("2")
    }

    // FIXME: This test fails because IllegalStateException is thrown and it can't be handled by test
    @Test(expected = IllegalStateException::class)
    fun null_event_id_throws_exception() {
        assertThrows(IllegalStateException::class.java) {
            launchFragment(null)
        }
    }

    private fun launchFragment(eventId: String?) {
        launchFragmentInContainer(
            bundleOf(EVENT_ID to eventId),
            designR.style.Theme_AndroidPractice
        ) {
            spyk<NewsDetailsFragment> {
                every { injectViewModelFactory() } answers {}
                every { viewModelFactory } returns viewModelProvider
            }
        }
    }
}
