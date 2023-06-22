package com.example.androidpractice.ui

import android.widget.ImageButton
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import com.example.androidpractice.R
import com.example.androidpractice.screen.help.HelpFragment
import com.example.androidpractice.screen.profile.ProfileFragment
import com.example.androidpractice.screen.search.SearchFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.Stack

class NavController(
    private val bottomNavigationView: BottomNavigationView,
    private val fragmentManager: FragmentManager,
    private val helpImageView: ImageButton,
    @IdRes private val containerId: Int
) {

    private val backStackMap = mutableMapOf<Int, Stack<Fragment>>()
    private val bottomNavStack = Stack<Int>()

    init {
        initNavigation()
        bottomNavigationView.selectedItemId = HELP_DEST
    }

    /**
     * Replace current fragment with previous or does nothing if there are no fragments
     *
     * @return [true] if the fragment was replaced, [false] - otherwise
     */
    fun onBackPressed(): Boolean {
        if (bottomNavStack.size == 1 && backStackMap[HELP_DEST]?.size == 1) {
            return false // finish application
        }
        val currentItemId = bottomNavigationView.selectedItemId
        val currentBackStackOfBottomItem =
            if (backStackMap.containsKey(currentItemId)) backStackMap[currentItemId] else null

        when {
            currentBackStackOfBottomItem?.size == 1 -> {
                // restore previous backstack
                backStackMap.remove(currentItemId)
                bottomNavStack.pop() // pop current item
                val backStackId = bottomNavStack.pop() // pop actual item, then it will be added
                fragmentManager.popBackStack(currentItemId, 0)
                bottomNavigationView.selectedItemId = backStackId
            }
            currentBackStackOfBottomItem != null && currentBackStackOfBottomItem.size > 1 -> {
                // TODO: test when there will be nested navigation
                fragmentManager.commit {
                    currentBackStackOfBottomItem.pop()
                    replace(containerId, currentBackStackOfBottomItem.last())
                }
                return true
            }
            currentBackStackOfBottomItem == null -> {
                return false
            }
        }
        return true
    }

    /**
     * Nested navigation
     *
     * @param fragment
     */
    fun navigate(fragment: BaseFragment) {
        fragmentManager.commit {
            setReorderingAllowed(true)
            replace(containerId, fragment)
            addToBackStack(fragment.bottomNavigationId.toString())
            backStackMap[fragment.bottomNavigationId]?.push(fragment)
        }
    }

    private fun navigateToBottomDestination(backStackId: Int): Boolean {
        bottomNavStack.push(backStackId)
        if (backStackMap.containsKey(backStackId)) {
            fragmentManager.restoreBackStack(backStackId.toString())
            return true
        } else {
            getFragmentById(backStackId)?.let { fragment ->
                fragmentManager.commit {
                    setReorderingAllowed(true)
                    replace(containerId, fragment)
                    addToBackStack(backStackId.toString())
                    backStackMap[backStackId] = Stack<Fragment>().apply { push(fragment) }
                }
                return true
            } ?: return false
        }
    }

    private fun initNavigation() {
        bottomNavigationView.setOnItemSelectedListener { menu ->
            val currentId = bottomNavigationView.selectedItemId
            val newId = menu.itemId
            if (newId == currentId) {
                return@setOnItemSelectedListener false
            }
            if (newId != NEWS_DEST && newId != HISTORY_DEST) { // TODO: Remove when implement
                fragmentManager.saveBackStack(bottomNavigationView.selectedItemId.toString())
            }
            navigateToBottomDestination(newId)
        }
        helpImageView.setOnClickListener {
            bottomNavigationView.selectedItemId = HELP_DEST
        }
    }

    private fun getFragmentById(id: Int): Fragment? {
        return when (id) {
            HELP_DEST -> HelpFragment.newInstance()
            NEWS_DEST -> null
            SEARCH_DEST -> SearchFragment.newInstance()
            PROFILE_DEST -> ProfileFragment.newInstance()
            HISTORY_DEST -> null
            else -> null
        }
    }

    companion object {
        private val HELP_DEST = R.id.helpNavItem
        private val NEWS_DEST = R.id.newsNavItem
        private val SEARCH_DEST = R.id.searchNavItem
        private val PROFILE_DEST = R.id.profileNavItem
        private val HISTORY_DEST = R.id.historyNavItem
        private const val TAG = "NavController"
    }
}