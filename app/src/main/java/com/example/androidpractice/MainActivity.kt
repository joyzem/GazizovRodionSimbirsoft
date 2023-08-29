package com.example.androidpractice

import android.os.Build
import android.os.Bundle
import android.window.OnBackInvokedDispatcher
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.androidpractice.core.ui.navigation.BottomBaseNavController
import com.example.androidpractice.core.ui.navigation.NavController
import com.example.androidpractice.databinding.ActivityMainBinding
import com.example.androidpractice.feature.auth.AuthFragment
import com.example.androidpractice.ui.navigation.BottomNavController
import javax.inject.Inject
import com.example.androidpractice.core.ui.R as uiR

class MainActivity : AppCompatActivity(), NavController.NavControllerOwner {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: BottomBaseNavController

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: MainViewModel by viewModels {
        viewModelFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as App).appComponent.inject(this)
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        navController = BottomNavController.newInstance(
            binding.bottomNavView,
            supportFragmentManager,
            binding.fragmentContainer,
            binding.helpButton,
            R.id.fragmentContainer,
            savedInstanceState
        )

        observe()
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
            AuthFragment.LOGIN_BUTTON_CLICKED,
            this
        ) { _, _ ->
            navController.navigateToBottomDestination(uiR.id.helpNavItem, true)
        }
    }

    private fun observe() {
        viewModel.unreadNewsCounter.observe(this) { counter ->
            if (counter == 0) {
                binding.bottomNavView.removeBadge(uiR.id.newsNavItem)
            } else {
                val badge = binding.bottomNavView.getOrCreateBadge(uiR.id.newsNavItem)
                badge.number = counter
            }
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
