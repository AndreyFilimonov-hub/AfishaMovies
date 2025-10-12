package com.filimonov.afishamovies.presentation.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import com.filimonov.afishamovies.R
import com.filimonov.afishamovies.databinding.ActivityMainBinding
import com.filimonov.afishamovies.presentation.ui.filmpage.FilmPageFragment
import com.filimonov.afishamovies.presentation.ui.filmpage.FilmPageMode
import com.filimonov.afishamovies.presentation.ui.homepage.HomePageFragment
import com.filimonov.afishamovies.presentation.ui.onboard.OnBoardFragment

class MainActivity : AppCompatActivity() {

    companion object {

        private const val IS_FIRST_LAUNCH = "is_first_launch"
        private const val APP_PREFS = "app_prefs"
        private const val HOME_PAGE_TAG = "HomePageFragment"
    }

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
                        val homePageFragments = fragments.filterIsInstance<HomePageFragment>()
                        val exists = homePageFragments.isEmpty()
                        if (exists) {
                            launchHomePageFragment()
                            return@setOnItemSelectedListener true
                        }
                        if (fragments.isNotEmpty()) {
                            val topFragment = fragments.last()
                            val homePageFragment =
                                fragmentManager.findFragmentByTag(HOME_PAGE_TAG)
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
        val prefs = getSharedPreferences(APP_PREFS, MODE_PRIVATE)
        return prefs.getBoolean(IS_FIRST_LAUNCH, true)
    }

    fun setFirstLaunchShown() {
        val prefs = getSharedPreferences(APP_PREFS, MODE_PRIVATE)
        prefs.edit { putBoolean(IS_FIRST_LAUNCH, false) }
    }

    private fun handleDeepLink(intent: Intent): Boolean {
        val data: Uri = intent.data ?: return false

        if (data.host == "afisha.app" && data.path?.startsWith("/film") == true) {
            val movieId = data.getQueryParameter("movieId")?.toIntOrNull()
            if (movieId != null) {
                supportFragmentManager.beginTransaction()
                    .add(
                        R.id.fragment_container, FilmPageFragment.newInstance(
                            movieId,
                            FilmPageMode.FROM_DEEPLINK.name
                        )
                    )
                    .addToBackStack(null)
                    .commit()
                return true
            }
        }

        return false
    }

    private fun launchOnBoardFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, OnBoardFragment())
            .commit()
    }

    private fun launchHomePageFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, HomePageFragment.newInstance(), HOME_PAGE_TAG)
            .commit()
    }
}