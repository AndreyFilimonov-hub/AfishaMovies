package com.filimonov.afishamovies.presentation.ui.homepage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.transition.TransitionManager
import com.filimonov.afishamovies.AfishaMoviesApp
import com.filimonov.afishamovies.R
import com.filimonov.afishamovies.databinding.FragmentHomePageBinding
import com.filimonov.afishamovies.presentation.ui.MainActivity
import com.filimonov.afishamovies.presentation.ui.filmpage.FilmPageFragment
import com.filimonov.afishamovies.presentation.ui.filmpage.FilmPageMode
import com.filimonov.afishamovies.presentation.ui.homepage.sectionadapter.SectionAdapter
import com.filimonov.afishamovies.presentation.ui.listpage.ListPageFragment
import com.filimonov.afishamovies.presentation.ui.listpage.ListPageMode
import com.filimonov.afishamovies.presentation.utils.ViewAnimator
import com.filimonov.afishamovies.presentation.utils.ViewModelFactory
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomePageFragment : Fragment() {

    companion object {

        @JvmStatic
        fun newInstance() =
            HomePageFragment()
    }

    private var _binding: FragmentHomePageBinding? = null
    private val binding: FragmentHomePageBinding
        get() = _binding ?: throw RuntimeException("FragmentHomePageBinding == null")

    private val component by lazy {
        (requireActivity().application as AfishaMoviesApp).component
            .homePageComponent()
            .create()
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[HomePageViewModel::class.java]
    }

    private val sectionAdapter = SectionAdapter(
        onShowAllClick = {
            val listPageFragment =
                ListPageFragment.newInstance(it.categoryId, getString(it.title), ListPageMode.MEDIA)
            (requireActivity() as MainActivity).openFragment(listPageFragment)
        },
        onMediaClick = {
            viewModel.addMediaBannerToInterestedCollection(it)
            val filmPageFragment = FilmPageFragment.newInstance(it.id, FilmPageMode.DEFAULT.name)
            (requireActivity() as MainActivity).openFragment(filmPageFragment)
        }
    )

    private var shortAnimationDuration: Long = 0

    private val viewAnimator = ViewAnimator()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        component.inject(this)

        (requireActivity() as? MainActivity)?.setFirstLaunchShown()

        shortAnimationDuration =
            resources.getInteger(android.R.integer.config_shortAnimTime).toLong()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomePageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setInsets()
        setPaddingRootView()
        observeViewModel()

        binding.rvSections.adapter = sectionAdapter

        binding.buttonReload.setOnClickListener {
            viewModel.reloadData()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.success) { view, insets ->
            val displayCutout = insets.getInsets(WindowInsetsCompat.Type.displayCutout())
            view.updatePadding(top = displayCutout.top)
            insets
        }
    }

    private fun setPaddingRootView() {
        val bottomNavigationView = requireActivity().findViewById<BottomNavigationView>(R.id.bNav)
        val rootView = binding.root

        bottomNavigationView.post {
            val bottomHeight = bottomNavigationView.height

            val layoutParams = rootView.layoutParams as ViewGroup.MarginLayoutParams
            layoutParams.bottomMargin = bottomHeight
            rootView.layoutParams = layoutParams
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.state.collect {
                    TransitionManager.beginDelayedTransition(binding.success)
                    when (it) {
                        is HomePageState.Loading -> {
                            with(viewAnimator) {
                                setupVisibilityGone(binding.error, shortAnimationDuration)
                                setupVisibilityGone(binding.success, shortAnimationDuration)
                                setupVisibilityVisible(binding.loading, shortAnimationDuration)
                            }
                        }

                        HomePageState.Error -> {
                            with(viewAnimator) {
                                setupVisibilityGone(binding.loading, shortAnimationDuration)
                                setupVisibilityVisible(binding.error, shortAnimationDuration)
                            }
                        }

                        is HomePageState.Success -> {
                            with(viewAnimator) {
                                setupVisibilityGone(binding.loading, shortAnimationDuration)
                                setupVisibilityVisible(binding.success, shortAnimationDuration)
                            }

                            sectionAdapter.submitList(it.categories)
                        }
                    }
                }
            }
        }
    }

    fun scrollToTop() {
        binding.success.smoothScrollTo(0, 0)
    }
}