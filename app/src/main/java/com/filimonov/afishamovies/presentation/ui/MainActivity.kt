package com.filimonov.afishamovies.presentation.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.filimonov.afishamovies.R
import com.filimonov.afishamovies.databinding.ActivityMainBinding
import com.filimonov.afishamovies.presentation.ui.filmpage.FilmPageFragment
import com.filimonov.afishamovies.presentation.ui.filmpage.FilmPageMode
import com.filimonov.afishamovies.presentation.ui.homepage.HomePageFragment
import com.filimonov.afishamovies.presentation.ui.onboardpage.OnBoardPageFragment
import com.filimonov.afishamovies.presentation.ui.profilepage.ProfilePageFragment
import com.filimonov.afishamovies.presentation.ui.searchpage.SearchPageFragment
import com.filimonov.afishamovies.presentation.ui.searchpage.searchsettingsfragment.SearchSettingsFragment

class MainActivity : AppCompatActivity() {

    companion object {

        private const val IS_FIRST_LAUNCH = "is_first_launch"
        private const val APP_PREFS = "app_prefs"
    }

    private val homeStack = mutableListOf<Fragment>()
    private val searchStack = mutableListOf<Fragment>()
    private val profileStack = mutableListOf<Fragment>()

    private var currentStack = homeStack

    private var isBottomBarVisible = false

    private var pendingDeepLink: Intent? = null

    val binging: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binging.root)
        setInsets()
        setupBackPressed()
        setOnBottomNavigationBarItemsClickListener()
        setupOnBackStackChangedListener()

        if (isFirstLaunch()) {
            initFragments()
            pendingDeepLink = intent
            launchOnBoardFragment()
            return
        }

        initFragments()

        if (!handleDeepLink(intent)) {
            launchHomePageFragment()
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        handleDeepLink(intent)
    }

    private fun setInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(binging.bNav) { view, insets ->
            val navBar = insets.getInsets(WindowInsetsCompat.Type.navigationBars())
            view.updatePadding(bottom = navBar.bottom)
            insets
        }
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
        val homePageFragment = HomePageFragment.newInstance()
        val searchPageFragment = SearchPageFragment.newInstance()
        val profilePageFragment = ProfilePageFragment.newInstance()

        homeStack.add(homePageFragment)
        searchStack.add(searchPageFragment)
        profileStack.add(profilePageFragment)

        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container, homePageFragment)
            .add(R.id.fragment_container, searchPageFragment)
            .add(R.id.fragment_container, profilePageFragment)
            .hide(searchPageFragment)
            .hide(profilePageFragment)
            .commitNow()
    }

    private fun switchTab(stack: MutableList<Fragment>) {
        if (stack == currentStack) {
            onRetryClick(currentStack.last())
            return
        }

        val transaction = getFragmentTransactionWithSwitchTabAnimation(stack)
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

    private fun getFragmentTransactionWithSwitchTabAnimation(stack: MutableList<Fragment>): FragmentTransaction {
        return supportFragmentManager.beginTransaction().apply {
            when {
                currentStack == homeStack && stack == searchStack -> this.setCustomAnimations(
                    R.anim.slide_in_from_right,
                    R.anim.slide_out_to_left,
                    0,
                    0
                )

                currentStack == homeStack && stack == profileStack -> this.setCustomAnimations(
                    R.anim.slide_in_from_right,
                    R.anim.slide_out_to_left,
                    0,
                    0
                )

                currentStack == searchStack && stack == homeStack -> this.setCustomAnimations(
                    R.anim.slide_in_from_left,
                    R.anim.slide_out_to_right,
                    0,
                    0
                )

                currentStack == searchStack && stack == profileStack -> this.setCustomAnimations(
                    R.anim.slide_in_from_right,
                    R.anim.slide_out_to_left,
                    0,
                    0
                )

                currentStack == profileStack && stack == searchStack -> this.setCustomAnimations(
                    R.anim.slide_in_from_left,
                    R.anim.slide_out_to_right,
                    0,
                    0
                )

                currentStack == profileStack && stack == homeStack -> this.setCustomAnimations(
                    R.anim.slide_in_from_left,
                    R.anim.slide_out_to_right,
                    0,
                    0
                )
            }
        }
    }

    private fun navigateToRootOfCurrentTab() {
        if (currentStack.size < 2) return

        val transaction = supportFragmentManager.beginTransaction()
            .setReorderingAllowed(true)
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
            if (fragment != rootFragment && fragment != lastFragment) {
                transaction.remove(fragment)
            }
        }

        transaction.show(rootFragment)
        transaction.hide(lastFragment)

        transaction.commitAllowingStateLoss()

        current.retainAll(listOf(rootFragment))
        supportFragmentManager.fragments.retainAll(current)
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

        setVisibleBottomNavBar(fragment)
    }

    fun closeFragment() {
        supportFragmentManager.popBackStack()
    }

    private fun isFirstLaunch(): Boolean {
        val prefs = getSharedPreferences(APP_PREFS, MODE_PRIVATE)
        return prefs.getBoolean(IS_FIRST_LAUNCH, true)
    }

    private fun setFirstLaunchShown() {
        val prefs = getSharedPreferences(APP_PREFS, MODE_PRIVATE)
        prefs.edit { putBoolean(IS_FIRST_LAUNCH, false) }
    }

    private fun handleDeepLink(intent: Intent): Boolean {
        val data: Uri = intent.data ?: return false

        if (data.host == "afisha.app" && data.path?.startsWith("/film") == true) {
            val movieId = data.getQueryParameter("movieId")?.toIntOrNull()
            if (movieId != null) {
                val filmPageFragment = FilmPageFragment.newInstance(
                    movieId,
                    FilmPageMode.FROM_DEEPLINK.name
                )
                currentStack.add(filmPageFragment)
                supportFragmentManager.beginTransaction()
                    .setCustomAnimations(
                        android.R.anim.fade_in,
                        R.anim.no_anim,
                        R.anim.slide_in_from_left,
                        R.anim.slide_out_to_right
                    )
                    .add(R.id.fragment_container, filmPageFragment)
                    .addToBackStack(null)
                    .hide(currentStack[currentStack.size - 2])
                    .commit()

                showBottomNavBar()

                return true
            }
        }

        return false
    }

    private fun setVisibleBottomNavBar(fragment: Fragment) {
        if (fragment is SearchPageFragment || fragment is HomePageFragment) {
            showBottomNavBar()
        } else if (fragment is SearchSettingsFragment) {
            hideBottomNavBar()
        }
    }

    private fun showBottomNavBar() {
        if (isBottomBarVisible) return

        val bNav = binging.bNav

        bNav.apply {
            visibility = View.VISIBLE
            translationY = height.toFloat()
        }
            .animate()
            .translationY(0f)
            .setDuration(1000)
            .start()

        isBottomBarVisible = true
    }

    private fun hideBottomNavBar() {
        if (!isBottomBarVisible) return

        val bNav = binging.bNav
        val height = bNav.height.toFloat()

        bNav.apply {
            visibility = View.VISIBLE
            translationY = 0f
        }
            .animate()
            .translationY(height)
            .setDuration(1000)
            .start()

        isBottomBarVisible = false
    }

    private fun setupBackPressed() {
        onBackPressedDispatcher.addCallback {
            closeFragment()
        }
    }

    private fun setupOnBackStackChangedListener() {
        supportFragmentManager.addOnBackStackChangedListener {
            val topFragment = supportFragmentManager.fragments.lastOrNull { it.isVisible }
            topFragment?.let {
                currentStack.removeAll { !it.isAdded }
                setVisibleBottomNavBar(topFragment)
                WindowInsetsControllerCompat(window, window.decorView)
                    .isAppearanceLightStatusBars = true
            }
        }
    }

    private fun launchOnBoardFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, OnBoardPageFragment.newInstance())
            .commit()
    }

    private fun launchHomePageFragment(fromOnBoard: Boolean = false) {
        showBottomNavBar()
        val homePageFragment = homeStack.first()
        val transaction = supportFragmentManager.beginTransaction()

        if (fromOnBoard) {
            transaction
                .setCustomAnimations(
                    R.anim.slide_in_from_right,
                    R.anim.slide_out_to_left,
                    R.anim.slide_in_from_right,
                    R.anim.slide_out_to_left
                )
                .replace(R.id.fragment_container ,homePageFragment)
                .commit()
        } else {
            transaction
                .show(homePageFragment)
                .commit()
        }
    }

    fun onOnBoardFinished() {
        pendingDeepLink?.let {
            launchHomePageFragment(true)
            handleDeepLink(it)
        } ?: launchHomePageFragment(true)

        setFirstLaunchShown()
    }
}