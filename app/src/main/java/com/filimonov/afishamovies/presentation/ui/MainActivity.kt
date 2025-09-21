package com.filimonov.afishamovies.presentation.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.filimonov.afishamovies.R
import com.filimonov.afishamovies.databinding.ActivityMainBinding
import com.filimonov.afishamovies.presentation.ui.homepage.HomePageFragment
import com.filimonov.afishamovies.presentation.ui.onboard.OnBoardFragment

class MainActivity : AppCompatActivity() {

    val binging: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binging.root)
        launchOnBoardFragment()
        setOnBottomNavigationBarItemsClickListener()
    }

    private fun setOnBottomNavigationBarItemsClickListener() {
        binging.bNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.item_home -> {
                    val fragment = supportFragmentManager.findFragmentById(R.id.fragment_container)

                    if (fragment is HomePageFragment) {
                        fragment.scrollToTop()
                    } else {
                        supportFragmentManager.popBackStack(
                            "HomePageFragment",
                            FragmentManager.POP_BACK_STACK_INCLUSIVE
                        )
                    }
                    true
                }

                R.id.item_search -> {
                    // TODO launch: SearchFragment
                    true
                }

                R.id.item_profile -> {
                    // TODO launch: ProfileFragment
                    true
                }

                else -> false
            }
        }
    }

    private fun launchOnBoardFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, OnBoardFragment())
            .commit()
    }
}