package com.filimonov.afishamovies.presentation.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import com.filimonov.afishamovies.R
import com.filimonov.afishamovies.databinding.ActivityMainBinding
import com.filimonov.afishamovies.presentation.ui.filmpage.FilmPageFragment
import com.filimonov.afishamovies.presentation.ui.filmpage.FilmPageMode
import com.filimonov.afishamovies.presentation.ui.homepage.HomePageFragment
import com.filimonov.afishamovies.presentation.ui.onboard.OnBoardFragment
import com.filimonov.afishamovies.presentation.ui.searchpage.SearchPageFragment

class MainActivity : AppCompatActivity() {

    companion object {

        private const val IS_FIRST_LAUNCH = "is_first_launch"
        private const val APP_PREFS = "app_prefs"
        private const val HOME_PAGE_TAG = "HomePageFragment"
    }

    private val homeStack = mutableListOf<Fragment>()
    private val searchStack = mutableListOf<Fragment>()
    private val profileStack = mutableListOf<Fragment>()

    private var currentStack = homeStack

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

        initFragments()

        setOnBottomNavigationBarItemsClickListener()
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleDeepLink(intent)
    }

    private fun setOnBottomNavigationBarItemsClickListener() {
        binging.bNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.item_home -> switchTab(homeStack)

                R.id.item_search -> switchTab(searchStack)

                R.id.item_profile -> switchTab(profileStack)
            }
            true
        }
    }

    private fun initFragments() {
        searchStack.add(SearchPageFragment.newInstance())
        // TODO: profileFragment
    }

    private fun switchTab(stack: MutableList<Fragment>) {
        if (stack == currentStack) {
            onRetryClick(currentStack.last())
            return
        }

        val transaction = supportFragmentManager.beginTransaction()
        currentStack.forEach { transaction.hide(it) }

        val fragmentToShow = stack.last()
        if (!fragmentToShow.isAdded) {
            transaction.add(R.id.fragment_container, fragmentToShow)
        } else {
            transaction.show(stack.last())
        }

        transaction.commit()
        currentStack = stack
    }

    private fun onRetryClick(lastOpenFragment: Fragment) {
        when (lastOpenFragment) {
            in homeStack -> {
                if (lastOpenFragment is HomePageFragment) {
                    lastOpenFragment.scrollToTop()
                } else {
                    navigateToRootOfCurrentTab()
                }
            }

            in searchStack -> navigateToRootOfCurrentTab()

            in profileStack -> navigateToRootOfCurrentTab()
        }
    }

    private fun navigateToRootOfCurrentTab() {
        if (currentStack.size < 2) return

        val transaction = supportFragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.no_anim,
                R.anim.slide_out_to_right,
                R.anim.no_anim,
                R.anim.slide_out_to_right
            )

        val current = currentStack

        val rootFragment = current.first()
        val lastFragment = current.last()

        current.forEach { fragment ->
            if (fragment != current.first() && fragment != current.last()) {
                transaction.remove(fragment)
            }
        }

        transaction.show(rootFragment)
        transaction.hide(lastFragment)

        transaction.commitAllowingStateLoss()

        current.retainAll(listOf(rootFragment))
    }

    fun openFragment(fragment: Fragment) {
        currentStack.add(fragment)
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.slide_in_from_right,
                R.anim.no_anim,
                R.anim.no_anim,
                R.anim.slide_out_to_right
            )
            .add(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .hide(currentStack[currentStack.size - 2])
            .commit()
    }

    fun closeFragment(fragment: Fragment) {
        supportFragmentManager.popBackStack()
        supportFragmentManager.addOnBackStackChangedListener {
            currentStack.remove(fragment)
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
        val homePageFragment = HomePageFragment.newInstance()
        homeStack.add(homePageFragment)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, homePageFragment, HOME_PAGE_TAG)
            .commit()
    }
}