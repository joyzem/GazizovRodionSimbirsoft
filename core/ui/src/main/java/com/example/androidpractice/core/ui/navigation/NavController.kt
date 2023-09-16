package com.example.androidpractice.core.ui.navigation

import android.os.Bundle
import com.example.androidpractice.core.ui.BaseFragment

interface NavController {

    /**
     * Replace current fragment with previous or does nothing if there are no fragments
     *
     * @return [true] if the fragment was replaced, [false] - otherwise
     */
    fun onBackPressed(): Boolean

    /**
     * Nested navigation
     *
     * @param fragment
     */
    fun navigate(fragment: BaseFragment<*, *>)

    fun navigateDeepLink(fragments: List<BaseFragment<*, *>>)

    fun navigateToBottomDestination(bottomNavId: Int, popCurrent: Boolean = false)

    fun restoreState(savedInstanceState: Bundle)

    fun onSaveInstanceState(outState: Bundle)

    interface NavControllerOwner {
        fun getNavController(): NavController
    }
}
