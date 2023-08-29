package com.example.androidpractice.ui.navigation

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.annotation.IdRes
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.FragmentManager
import com.example.androidpractice.R
import com.example.androidpractice.core.ui.BaseFragment
import com.example.androidpractice.core.ui.navigation.BottomBaseNavController
import com.example.androidpractice.feature.auth.AuthFragment
import com.example.androidpractice.feature.help.HelpFragment
import com.example.androidpractice.feature.news.NewsFragment
import com.example.androidpractice.feature.profile.ProfileFragment
import com.example.androidpractice.feature.search.SearchFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class BottomNavController private constructor(
    private val bottomNavigationView: BottomNavigationView,
    private val fragmentManager: FragmentManager,
    private val fragmentContainer: FragmentContainerView,
    private val helpImageView: ImageButton,
    @IdRes private val containerId: Int
) : BottomBaseNavController(
    bottomNavigationView,
    fragmentManager,
    fragmentContainer,
    helpImageView,
    containerId,
    R.id.helpNavItem,
    R.id.historyNavItem
) {
    init {
        fragmentManager.addOnBackStackChangedListener {
            val fragment = fragmentManager.findFragmentById(containerId)
            if (fragment is BaseFragment<*, *> && fragment.hideBottomNavigationView) {
                bottomNavigationView.visibility = View.GONE
                helpImageView.visibility = View.GONE
                fragmentContainer.updatePadding(bottom = 0)
            } else {
                bottomNavigationView.visibility = View.VISIBLE
                helpImageView.visibility = View.VISIBLE
                fragmentContainer.updatePadding(bottom = bottomNavigationView.height)
            }
        }
    }

    override fun getFragmentByBottomNavId(id: Int): Fragment? {
        return when (id) {
            HELP_DEST -> HelpFragment.newInstance()
            NEWS_DEST -> NewsFragment.newInstance()
            SEARCH_DEST -> SearchFragment.newInstance()
            PROFILE_DEST -> ProfileFragment.newInstance()
            HISTORY_DEST -> null
            else -> null
        }
    }

    override fun getFragmentBottomNavIdByMenuId(menuId: Int): Int {
        return when (menuId) {
            HELP_DEST -> com.example.androidpractice.feature.help.R.id.helpNavigation
            NEWS_DEST -> com.example.androidpractice.feature.news.R.id.newsNavigation
            SEARCH_DEST -> com.example.androidpractice.feature.search.R.id.searchNavigation
            PROFILE_DEST -> com.example.androidpractice.feature.profile.R.id.profileNavigation
            else -> {
                0
            }
        }
    }

    companion object {
        private val HELP_DEST = R.id.helpNavItem
        private val NEWS_DEST = R.id.newsNavItem
        private val SEARCH_DEST = R.id.searchNavItem
        private val PROFILE_DEST = R.id.profileNavItem
        private val HISTORY_DEST = R.id.historyNavItem

        fun newInstance(
            bottomNavigationView: BottomNavigationView,
            fragmentManager: FragmentManager,
            fragmentContainer: FragmentContainerView,
            helpImageView: ImageButton,
            @IdRes containerId: Int,
            savedInstanceState: Bundle?
        ): BottomNavController {
            return BottomNavController(
                bottomNavigationView,
                fragmentManager,
                fragmentContainer,
                helpImageView,
                containerId
            ).apply {
                initNavigation()
                if (savedInstanceState == null) {
                    navigate(AuthFragment.newInstance())
                } else {
                    restoreState(savedInstanceState)
                }
            }
        }
    }
}
