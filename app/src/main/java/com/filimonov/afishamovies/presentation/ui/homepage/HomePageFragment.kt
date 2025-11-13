package com.filimonov.afishamovies.presentation.ui.homepage

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.transition.Slide
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
            requireActivity().supportFragmentManager.beginTransaction()
                .addToBackStack(null)
                .add(
                    R.id.fragment_container,
                    ListPageFragment.newInstance(it.categoryId, it.title, ListPageMode.MEDIA)
                )
                .commit()
        },
        onMediaClick = {
            requireActivity().supportFragmentManager.beginTransaction()
                .addToBackStack(null)
                .add(
                    R.id.fragment_container,
                    FilmPageFragment.newInstance(it.id, FilmPageMode.DEFAULT.name)
                )
                .commit()
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        component.inject(this)
        enterTransition = Slide(Gravity.END).apply {
            duration = 500L
            interpolator = AccelerateInterpolator()
            propagation = null
        }
        exitTransition = Slide(Gravity.START).apply {
            duration = 500L
            interpolator = AccelerateInterpolator()
            propagation = null
        }

        (requireActivity() as? MainActivity)?.setFirstLaunchShown()
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

        setPaddingRootView()
        observeViewModel()

        binding.rvSections.adapter = sectionAdapter

        binding.buttonReload.setOnClickListener {
            viewModel.reloadData()
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
                            binding.error.visibility = View.INVISIBLE
                            binding.success.visibility = View.INVISIBLE
                            binding.loading.visibility = View.VISIBLE
                        }

                        HomePageState.Error -> {
                            binding.loading.visibility = View.INVISIBLE
                            binding.error.visibility = View.VISIBLE
                        }

                        is HomePageState.Success -> {
                            onBottomNav()

                            binding.loading.visibility = View.INVISIBLE
                            binding.success.visibility = View.VISIBLE

                            sectionAdapter.submitList(it.categories)
                        }
                    }
                }
            }
        }
    }

    private fun onBottomNav() {
        val bNav = (requireActivity() as MainActivity).binging.bNav
        bNav.apply {
            visibility = View.VISIBLE
            translationY = height.toFloat()
        }
            .animate()
            .translationY(0f)
            .setDuration(1000)
            .start()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            HomePageFragment()
    }

    fun scrollToTop() {
        binding.success.smoothScrollTo(0, 0)
    }
}