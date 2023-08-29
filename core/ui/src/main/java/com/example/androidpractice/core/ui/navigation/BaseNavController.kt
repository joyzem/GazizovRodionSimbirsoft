package com.example.androidpractice.core.ui.navigation

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.annotation.IdRes
import androidx.core.view.isVisible
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import com.example.androidpractice.core.ui.BaseFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.Stack

abstract class BaseNavController(
    private val bottomNavigationView: BottomNavigationView,
    private val fragmentManager: FragmentManager,
    private val fragmentContainer: FragmentContainerView,
    private val helpImageView: ImageButton,
    @IdRes private val containerId: Int,
    private val startDestinationId: Int,
    private val historyNavId: Int // TODO: remove. Added due to modularization issue
) {
    private var backStackMap = mutableMapOf<Int, Int>()
    private var bottomNavStack = Stack<Int>()

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

    /**
     * Replace current fragment with previous or does nothing if there are no fragments
     *
     * @return [true] if the fragment was replaced, [false] - otherwise
     */
    fun onBackPressed(): Boolean {
        if (bottomNavStack.size == 1) {
            return false // finish application
        }
        val currentItemId = bottomNavigationView.selectedItemId
        val currentBackStackOfBottomItem =
            if (backStackMap.containsKey(currentItemId)) backStackMap[currentItemId] else null
        when {
            currentBackStackOfBottomItem == 1 -> {
                // restore previous backstack
                backStackMap.remove(currentItemId)
                bottomNavStack.pop() // pop current item
                val backStackId = bottomNavStack.pop() // pop actual item, then it will be added
                if (!bottomNavStack.contains(currentItemId)) {
                    fragmentManager.popBackStack()
                    fragmentManager.clearBackStack(currentItemId.toString())
                }
                bottomNavigationView.selectedItemId = backStackId
            }

            currentBackStackOfBottomItem != null && currentBackStackOfBottomItem > 1 -> {
                fragmentManager.popBackStack()
                if (backStackMap.containsKey(currentItemId)) {
                    backStackMap[currentItemId] = backStackMap[currentItemId]!! - 1
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
    fun navigate(fragment: BaseFragment<*, *>) {
        fragmentManager.commit {
            setReorderingAllowed(true)
            replace(containerId, fragment)
            addToBackStack(fragment.bottomNavigationId.toString())
            backStackMap[fragment.bottomNavigationId] =
                backStackMap.getOrDefault(fragment.bottomNavigationId, 1) + 1
        }
    }

    fun navigateToBottomDestination(bottomNavId: Int, popCurrent: Boolean = false) {
        if (popCurrent) {
            fragmentManager.popBackStack()
        }
        bottomNavigationView.selectedItemId = bottomNavId
    }

    fun restoreState(savedInstanceState: Bundle) {
        val bottomNavStack =
            savedInstanceState.getSerializable(BOTTOM_NAV_STACK) as? Stack<Int> ?: return
        val backStackMapKeys =
            savedInstanceState.getIntArray(BACK_STACK_MAP_KEYS) ?: intArrayOf()
        val map = mutableMapOf<Int, Int>()
        backStackMapKeys.forEach { key ->
            val fragmentsAmount = savedInstanceState.getInt("$BACK_STACK_VALUES_OF$key")
            map[key] = fragmentsAmount
        }

        // restore visibility of bottom navigation
        val fragment = fragmentManager.findFragmentById(containerId) as? BaseFragment<*, *>
        val isVisible = fragment?.let {
            !it.hideBottomNavigationView
        } ?: false
        bottomNavigationView.isVisible = isVisible
        helpImageView.isVisible = isVisible

        this.bottomNavStack = bottomNavStack
        this.backStackMap = backStackMap.toMutableMap()
    }

    fun onSaveInstanceState(outState: Bundle) {
        outState.putSerializable(BOTTOM_NAV_STACK, bottomNavStack)
        val keys = backStackMap.keys.toIntArray()
        outState.putIntArray(BACK_STACK_MAP_KEYS, keys)
        keys.forEach { key ->
            val stackFragmentsAmount = backStackMap[key]!!
            outState.putInt("$BACK_STACK_VALUES_OF$key", stackFragmentsAmount)
        }
    }

    private fun onBottomItemSelected(bottomItemId: Int): Boolean {
        bottomNavStack.push(bottomItemId)
        if (backStackMap.containsKey(bottomItemId)) {
            fragmentManager.restoreBackStack(bottomItemId.toString())
            return true
        } else {
            getFragmentById(bottomItemId)?.let { fragment ->
                fragmentManager.commit {
                    setReorderingAllowed(true)
                    replace(containerId, fragment)
                    addToBackStack(bottomItemId.toString())
                    backStackMap[bottomItemId] = 1
                }
                return true
            } ?: return false
        }
    }

    protected fun initNavigation() {
        bottomNavigationView.setOnItemSelectedListener { menu ->
            val currentId = bottomNavigationView.selectedItemId
            val newId = menu.itemId
            if (newId == currentId) {
                return@setOnItemSelectedListener false
            }
            if (newId != historyNavId) { // TODO: Remove when implement
                fragmentManager.saveBackStack(bottomNavigationView.selectedItemId.toString())
            }
            onBottomItemSelected(newId)
        }
        helpImageView.setOnClickListener {
            bottomNavigationView.selectedItemId = startDestinationId
        }
    }

    abstract fun getFragmentById(id: Int): Fragment?

    interface NavControllerOwner {
        fun getNavController(): BaseNavController
    }

    companion object {
        private const val BOTTOM_NAV_STACK = "bottomNavStack"
        private const val BACK_STACK_MAP_KEYS = "backStackMapKeys"
        private const val BACK_STACK_VALUES_OF = "backStackValuesOf"
    }
}

fun Fragment.findNavController() =
    (activity as BaseNavController.NavControllerOwner).getNavController()
