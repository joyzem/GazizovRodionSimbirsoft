package com.example.androidpractice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.example.androidpractice.databinding.ActivityMainBinding
import com.example.androidpractice.screen.profile.ProfileFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setupNavigation()
        navigate(R.id.profileNavItem)
    }

    private fun navigate(navItemId: Int) {
        binding.bottomNavView.selectedItemId = navItemId
    }

    private fun navigate(fragment: Class<out Fragment>) {
        supportFragmentManager.commit {
            replace(R.id.container, fragment, null)
        }
    }

    private fun setupNavigation() {
        binding.bottomNavView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.newsNavItem -> {
                    true
                }
                R.id.searchNavItem -> {
                    true
                }
                R.id.historyNavItem -> {
                    true
                }
                R.id.profileNavItem -> {
                    navigate(ProfileFragment::class.java)
                    true
                }
                else -> {
                    false
                }
            }
        }
        binding.helpButton.setOnClickListener {
            binding.bottomNavView.selectedItemId = R.id.helpNavItem
        }
    }
}