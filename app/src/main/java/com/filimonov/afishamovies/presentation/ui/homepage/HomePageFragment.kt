package com.filimonov.afishamovies.presentation.ui.homepage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.filimonov.afishamovies.R
import com.filimonov.afishamovies.databinding.FragmentHomePageBinding
import com.filimonov.afishamovies.presentation.ui.MainActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.launch

class HomePageFragment : Fragment() {

    private var _binding: FragmentHomePageBinding? = null
    private val binding: FragmentHomePageBinding
        get() = _binding ?: throw RuntimeException("FragmentHomePageBinding == null")

    private val viewModel by lazy {
        ViewModelProvider(this)[HomePageViewModel::class.java]
    }

    private val sectionAdapter = SectionAdapter(
        onShowAllClick = {
            // TODO: launch ListPageFragment
        },
        onMediaClick = {
            // TODO: launch MediaPageFragment
        }
    )


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomePageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setPaddingNestedScroll()
        observeViewModel()

        binding.rvSections.adapter = sectionAdapter

        binding.buttonReload.setOnClickListener {
            viewModel.reloadData()
        }
    }

    private fun setPaddingNestedScroll() {
        val bottomNavigationView = requireActivity().findViewById<BottomNavigationView>(R.id.bNav)
        val nestedScroll = binding.root

        bottomNavigationView.post {
            val bottomHeight = bottomNavigationView.height

            val layoutParams = nestedScroll.layoutParams as ViewGroup.MarginLayoutParams
            layoutParams.bottomMargin = bottomHeight
            nestedScroll.layoutParams = layoutParams
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.state.collect {
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
        val bindingMain = (requireActivity() as MainActivity).binging
        bindingMain.bNav.visibility = View.VISIBLE
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
}