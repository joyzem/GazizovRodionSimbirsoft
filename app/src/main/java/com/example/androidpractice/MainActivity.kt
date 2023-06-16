package com.example.androidpractice

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.example.androidpractice.databinding.ActivityMainBinding
import com.example.androidpractice.screen.help.HelpFragment
import com.example.androidpractice.screen.profile.ProfileFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setupNavigation()
        navigate(HelpFragment.newInstance()).also {
            binding.bottomNavView.selectedItemId = R.id.helpNavItem
        }
    }

    private fun navigate(fragment: Fragment) {
        supportFragmentManager.commit {
            replace(R.id.container, fragment, fragment.tag ?: fragment.javaClass.name)
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
                    navigate(ProfileFragment.newInstance())
                    true
                }

                else -> {
                    false
                }
            }
        }
        binding.helpButton.setOnClickListener {
            binding.bottomNavView.selectedItemId = R.id.helpNavItem
            navigate(HelpFragment.newInstance())
        }
    }
}
