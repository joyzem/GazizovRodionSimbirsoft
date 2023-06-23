package com.example.androidpractice.ui

import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

/**
 * Base fragment is used to identify the [BottomNavigationView] menu item
 *
 * @property bottomNavigationId
 * @constructor Create empty Base fragment
 */
abstract class BaseFragment(val bottomNavigationId: Int) : Fragment()
