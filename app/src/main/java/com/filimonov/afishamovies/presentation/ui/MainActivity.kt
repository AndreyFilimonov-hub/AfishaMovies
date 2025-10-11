package com.filimonov.afishamovies.presentation.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import com.filimonov.afishamovies.R
import com.filimonov.afishamovies.databinding.ActivityMainBinding
import com.filimonov.afishamovies.presentation.ui.filmpage.FilmPageFragment
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

        val isHandled = handleDeepLink(intent)
        if (!isHandled) {
            if (isFirstLaunch()) {
                launchOnBoardFragment()
            } else {
                launchHomePageFragment()
            }
        }

        setOnBottomNavigationBarItemsClickListener()
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleDeepLink(intent)
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

    private fun isFirstLaunch(): Boolean {
        val prefs = getSharedPreferences("app_prefs", MODE_PRIVATE)
        return prefs.getBoolean("is_first_launch", true)
    }

    fun setFirstLaunchShown() {
        val prefs = getSharedPreferences("app_prefs", MODE_PRIVATE)
        prefs.edit { putBoolean("is_first_launch", false) }
    }

    private fun launchOnBoardFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, OnBoardFragment())
            .commit()
    }

    private fun handleDeepLink(intent: Intent): Boolean {
        val data: Uri? = intent.data
        Log.d("AAA", data.toString())

        if (data == null) return false

        if (data.host == "afisha.app" && data.path?.startsWith("/film") == true) {
            val movieId = data.getQueryParameter("movieId")?.toIntOrNull()
            if (movieId != null) {
                supportFragmentManager.beginTransaction()
                    .add(R.id.fragment_container, FilmPageFragment.newInstance(movieId))
                    .addToBackStack(null)
                    .commit()
                return true
            }
        }

        return false
    }

    private fun launchHomePageFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, HomePageFragment.newInstance(), "HomePageFragment")
            .addToBackStack("HomePageFragment")
            .commit()
    }
}