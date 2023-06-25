package com.example.androidpractice.ui.navigation

import android.os.Bundle
import androidx.fragment.app.Fragment


/**
 * Stack fragment is used by [NavController] to navigate with FragmentManager using class of the fragment
 */
data class StackFragment(
    val fragment: Class<out Fragment>,
    val args: Bundle? = null
)