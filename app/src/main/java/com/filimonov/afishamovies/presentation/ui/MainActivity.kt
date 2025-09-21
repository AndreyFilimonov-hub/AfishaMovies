package com.filimonov.afishamovies.presentation.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
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
                    val fragmentManager = supportFragmentManager
                    val currentFragment = fragmentManager.findFragmentById(R.id.fragment_container)

                    if (currentFragment !is HomePageFragment) {
                        val fragments = fragmentManager.fragments
                        if (fragments.isNotEmpty()) {
                            val topFragment = fragments.last()
                            val homePageFragment =
                                fragmentManager.findFragmentByTag("HomePageFragment")
                            val transaction = fragmentManager.beginTransaction()

                            fragments.forEach { fragment ->
                                if (fragment != topFragment && fragment != homePageFragment) {
                                    transaction.remove(fragment)
                                }
                            }

                            transaction.remove(topFragment)
                            transaction.commit()
                        }
                    } else {
                        currentFragment.scrollToTop()
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

    private fun launchHomePageFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, HomePageFragment.newInstance(), "HomePageFragment")
            .addToBackStack("HomePageFragment")
            .commit()
    }
}