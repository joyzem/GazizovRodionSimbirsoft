package com.example.androidpractice.feature.profile

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.fragment.app.testing.withFragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import io.mockk.every
import io.mockk.spyk
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import com.example.androidpractice.core.designsystem.R as designR

@MediumTest
@RunWith(AndroidJUnit4::class)
class ProfileFragmentTest {

    private val viewModelProvider = object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ProfileViewModel() as T
        }
    }

    private lateinit var viewModel: ProfileViewModel

    @Before
    fun launchFragment() {
        launchFragmentInContainer(
            null,
            designR.style.Theme_AndroidPractice
        ) {
            val profileFragment = spyk<ProfileFragment> {
                every { injectViewModelFactory() } answers {}
                every { viewModelFactory } returns viewModelProvider
            }
            profileFragment
        }.also { scenario ->
            scenario.withFragment {
                this@ProfileFragmentTest.viewModel = viewModel
            }
        }
    }

    @Test
    fun friends_are_displayed_on_screen() {
        viewModel.friends.value!!.forEachIndexed { i, name ->
            onView(withId(R.id.friendRecyclerView))
                .perform(RecyclerViewActions.scrollToPosition<FriendsAdapter.FriendViewHolder>(i))
                .check(matches(hasDescendant(withText(name))))
        }
    }
}
