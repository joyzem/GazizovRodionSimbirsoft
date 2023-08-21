package com.example.androidpractice

import android.os.Build
import android.os.Bundle
import android.window.OnBackInvokedDispatcher
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.androidpractice.databinding.ActivityMainBinding
import com.example.androidpractice.di.ViewModelFactory
import com.example.androidpractice.screen.auth.AuthFragment
import com.example.androidpractice.ui.navigation.NavController
import javax.inject.Inject

class MainActivity : AppCompatActivity(), NavController.NavControllerOwner {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: MainViewModel by viewModels {
        viewModelFactory
    }

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
            navController.navigateToBottomDestination(R.id.helpNavItem, true)
        }
    }

    private fun observe() {
        viewModel.unreadNewsCounter.observe(this) { counter ->
            if (counter == 0) {
                binding.bottomNavView.removeBadge(R.id.newsNavItem)
            } else {
                val badge = binding.bottomNavView.getOrCreateBadge(R.id.newsNavItem)
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
