package com.example.androidpractice

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.example.androidpractice.databinding.ActivityMainBinding
import com.example.androidpractice.screen.help.HelpFragment
import com.example.androidpractice.screen.profile.ProfileFragment
import com.example.androidpractice.screen.search.SearchFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace<HelpFragment>(R.id.fragmentContainer)
        }
        binding.bottomNavView.selectedItemId = R.id.helpNavItem

        setupNavigation()
    }

    private fun setupNavigation() {
        binding.bottomNavView.setOnItemSelectedListener { menuItem ->
            val id = binding.bottomNavView.selectedItemId
            supportFragmentManager.saveBackStack(id.toString())

            when (menuItem.itemId) {
                R.id.newsNavItem -> {
                    true
                }

                R.id.searchNavItem -> {
                    supportFragmentManager.restoreBackStack(R.id.searchNavItem.toString()) //FIXME: async call
                    if (supportFragmentManager.findFragmentByTag("search") == null) {
                        supportFragmentManager.commit {
                            setReorderingAllowed(true)
                            replace<SearchFragment>(R.id.fragmentContainer, "search")
                            addToBackStack(R.id.searchNavItem.toString())
                        }
                    }
                    true
                }

                R.id.historyNavItem -> {
                    true
                }

                R.id.profileNavItem -> {
                    supportFragmentManager.restoreBackStack(R.id.profileNavItem.toString()) //FIXME: async call
                    if (supportFragmentManager.findFragmentByTag("profile") == null) {
                        supportFragmentManager.commit {
                            setReorderingAllowed(true)
                            replace<ProfileFragment>(R.id.fragmentContainer, "profile")
                            addToBackStack(R.id.profileNavItem.toString())
                        }
                    }
                    true
                }

                else -> {
                    false
                }
            }
        }
        binding.helpButton.setOnClickListener {
            val id = binding.bottomNavView.selectedItemId
            supportFragmentManager.saveBackStack(id.toString())
            supportFragmentManager.restoreBackStack(R.id.helpNavItem.toString())
            binding.bottomNavView.menu.getItem(2).isChecked = true
        }
    }
}
