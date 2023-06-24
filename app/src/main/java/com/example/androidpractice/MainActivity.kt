package com.example.androidpractice

import android.os.Build
import android.os.Bundle
import android.window.OnBackInvokedDispatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.example.androidpractice.databinding.ActivityMainBinding
import com.example.androidpractice.di.ViewModelsFactoryOwner
import com.example.androidpractice.ui.NavController
import javax.inject.Inject

class MainActivity : AppCompatActivity(), NavController.NavContollerOwner, ViewModelsFactoryOwner {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override val owner: ViewModelStoreOwner
        get() = this
    override val factory: ViewModelProvider.Factory
        get() = viewModelFactory


    override fun onCreate(savedInstanceState: Bundle?) {
        (application as App).appComponent.inject(this)
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        navController =
            NavController(
                binding.bottomNavView,
                supportFragmentManager,
                binding.helpButton,
                R.id.fragmentContainer
            )
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            onBackInvokedDispatcher.registerOnBackInvokedCallback(OnBackInvokedDispatcher.PRIORITY_DEFAULT) {
                if (!navController.onBackPressed()) {
                    finish()
                }
            }
        }
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
