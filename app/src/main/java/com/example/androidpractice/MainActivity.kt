package com.example.androidpractice

import android.os.Build
import android.os.Bundle
import android.window.OnBackInvokedDispatcher
import androidx.appcompat.app.AppCompatActivity
import com.example.androidpractice.databinding.ActivityMainBinding
import com.example.androidpractice.screen.auth.AuthFragment
import com.example.androidpractice.ui.navigation.NavController

class MainActivity : AppCompatActivity(), NavController.NavControllerOwner {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as App).appComponent.inject(this)
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        navController = NavController.newInstance(
            binding.bottomNavView,
            supportFragmentManager,
            binding.fragmentContainer,
            binding.helpButton,
            R.id.fragmentContainer,
            savedInstanceState
        )

        setFragmentResultListeners()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            onBackInvokedDispatcher.registerOnBackInvokedCallback(OnBackInvokedDispatcher.PRIORITY_DEFAULT) {
                if (!navController.onBackPressed()) {
                    finish()
                }
            }
        }
    }

    private fun setFragmentResultListeners() {
        supportFragmentManager.setFragmentResultListener(
            AuthFragment.BACK_CLICKED,
            this
        ) { requestKey, bundle ->
            finish()
        }
        supportFragmentManager.setFragmentResultListener(
            AuthFragment.LOGIN_BUTTON_CLICKED,
            this
        ) { key, bundle ->
            navController.navigateToBottomDestination(R.id.helpNavItem)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        navController.onSaveInstanceState(outState)
    }

    override fun getNavController(): NavController {
        return navController
    }

    override fun onBackPressed() {
        if (!navController.onBackPressed()) {
            finish()
        }
    }
}
