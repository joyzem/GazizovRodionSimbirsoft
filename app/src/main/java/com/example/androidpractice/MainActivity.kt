package com.example.androidpractice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.commit
import com.example.androidpractice.screen.profile.ProfileFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.commit {
            this.replace(R.id.container, ProfileFragment::class.java, null)
        }
    }
}