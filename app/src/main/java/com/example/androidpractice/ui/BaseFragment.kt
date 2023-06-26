package com.example.androidpractice.ui

import androidx.fragment.app.Fragment

abstract class BaseFragment(
    val bottomNavigationId: Int,
    val hideBottomNavigationView: Boolean = false
) : Fragment()
