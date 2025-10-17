package com.filimonov.afishamovies.presentation.ui.searchpage

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.filimonov.afishamovies.AfishaMoviesApp
import com.filimonov.afishamovies.R
import com.filimonov.afishamovies.databinding.FragmentSearchPageBinding
import com.filimonov.afishamovies.presentation.ui.searchpage.searchpageadapter.SearchItemAdapter
import com.filimonov.afishamovies.presentation.utils.ViewModelFactory
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchPageFragment : Fragment() {

    private var _binding: FragmentSearchPageBinding? = null

    private val binding: FragmentSearchPageBinding
        get() = _binding ?: throw RuntimeException("FragmentSearchPageBinding == null")

    private val component by lazy {
        (requireActivity().application as AfishaMoviesApp).component
            .searchPageComponent()
            .create()
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[SearchPageViewModel::class.java]
    }

    private val searchItemAdapter = SearchItemAdapter(
        onMediaBannerClick = {
            Log.d("AAA", it.toString())
        },
        onPersonBannerClick = {
            Log.d("AAA", it.toString())
        },
        onRetryButtonClick = {

        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        component.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchPageBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setPaddingRootView()
        setupSearchBar()
        setupRecyclerView()
        observeViewModel()
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.state.collect { state ->
                    when (state) {
                        SearchPageState.Empty -> {

                        }
                        SearchPageState.Error -> {

                        }
                        SearchPageState.Initial -> {

                        }
                        SearchPageState.Loading -> {

                        }
                        is SearchPageState.Success -> {
                            Log.d("AAA", state.result.toString())
                            searchItemAdapter.submitList(state.result)
                        }
                    }
                }
            }
        }
    }

    private fun setupRecyclerView() {
        binding.rvReplySearch.layoutManager = LinearLayoutManager(requireContext())

        binding.rvReplySearch.adapter = searchItemAdapter
    }

    private fun setupSearchBar() {
        binding.ivFilter.setOnClickListener {
            Toast.makeText(requireContext(), "filter", Toast.LENGTH_SHORT).show()
            parentFragmentManager.beginTransaction()
                .add(R.id.fragment_container, SearchSettingsFragment.newInstance("", ""))
                .addToBackStack(null)
                .commit()
        }
        binding.sbMain.doOnTextChanged { query, _, _, _ ->
            viewModel.sendRequest(query.toString())
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

    companion object {

        @JvmStatic
        fun newInstance() = SearchPageFragment()
    }
}