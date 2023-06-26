package com.example.androidpractice

import android.os.Build
import android.os.Bundle
import android.window.OnBackInvokedDispatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.example.androidpractice.databinding.ActivityMainBinding
import com.example.androidpractice.di.ViewModelsFactoryOwner
import com.example.androidpractice.ui.navigation.NavController
import com.example.androidpractice.ui.navigation.StackFragment
import java.util.Stack
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

        navController = NavController(
            binding.bottomNavView,
            supportFragmentManager,
            binding.fragmentContainer,
            binding.helpButton,
            R.id.fragmentContainer
        )

        savedInstanceState?.let { state ->
            val bottomNavStack =
                state.getSerializable(BOTTOM_NAV_STACK) as? Stack<Int> ?: return@let
            val backStackMapKeys = state.getIntArray(BACK_STACK_MAP_KEYS) ?: intArrayOf()
            val map = mutableMapOf<Int, Stack<StackFragment>>()
            backStackMapKeys.forEach { key ->
                map[key] =
                    state.getSerializable("$BACK_STACK_VALUES_OF$key") as Stack<StackFragment>
            }
            navController.restoreState(bottomNavStack = bottomNavStack, backStackMap = map)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            onBackInvokedDispatcher.registerOnBackInvokedCallback(OnBackInvokedDispatcher.PRIORITY_DEFAULT) {
                if (!navController.onBackPressed()) {
                    finish()
                }
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable(BOTTOM_NAV_STACK, navController.getBottomNavStack())
        val keys = navController.getBackStackMap().keys.toIntArray()
        outState.putIntArray(BACK_STACK_MAP_KEYS, keys)
        keys.forEach { key ->
            outState.putSerializable(
                "$BACK_STACK_VALUES_OF$key",
                navController.getBackStackMap()[key]
            )

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

    companion object {
        private const val BOTTOM_NAV_STACK = "bottomNavStack"
        private const val BACK_STACK_MAP_KEYS = "backStackMapKeys"
        private const val BACK_STACK_VALUES_OF = "backStackValuesOf"
    }
}
