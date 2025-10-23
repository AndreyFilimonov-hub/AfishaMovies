package com.filimonov.afishamovies.presentation.ui.searchpage.searchchoosefragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.Fade
import com.filimonov.afishamovies.AfishaMoviesApp
import com.filimonov.afishamovies.R
import com.filimonov.afishamovies.databinding.FragmentSearchChooseBinding
import com.filimonov.afishamovies.presentation.ui.searchpage.SearchSettingsFragment
import com.filimonov.afishamovies.presentation.utils.ViewModelFactory
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchChooseFragment : Fragment() {

    private lateinit var filterItem: String
    private lateinit var filterMode: FilterMode

    private var _binding: FragmentSearchChooseBinding? = null

    private val binding: FragmentSearchChooseBinding
        get() = _binding ?: throw RuntimeException("FragmentSearchChooseBinding == null")

    private val component by lazy {
        (requireActivity().application as AfishaMoviesApp).component
            .searchPageComponent()
            .create()
            .createSearchChooseComponent()
            .create(filterMode)
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[SearchChooseViewModel::class.java]
    }

    private val searchChooseAdapter by lazy {
        SearchChooseAdapter(
            filterItem,
            onItemClick = { chooseFilterItem ->
                filterItem = chooseFilterItem
                sendDataToPreviousFragment()
            }
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            val filterItemBundle = it.getString(FILTER_ITEM) ?: ""
            filterItem = filterItemBundle
            val filterModeBundle =
                it.getString(FILTER_MODE) ?: throw RuntimeException("param mode is absent")
            filterMode = FilterMode.valueOf(filterModeBundle)
        }

        component.inject(this)
        enterTransition = Fade()
        exitTransition = Fade()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchChooseBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        setupSearchBar()
        setupRecyclerView()
        observeViewModel()
        setupButtonReset()
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.state.collect { state ->
                    when (state) {
                        is SearchChooseState.Initial -> {
                            searchChooseAdapter.submitList(state.list)
                        }

                        is SearchChooseState.Search -> {
                            searchChooseAdapter.submitList(state.list)
                        }
                    }
                }
            }
        }
    }

    private fun setupToolbar() {
        setupBackButton()
        when (filterMode) {
            FilterMode.COUNTRY -> {
                binding.tvTitle.text = getString(R.string.country)
            }

            FilterMode.GENRE -> {
                binding.tvTitle.text = getString(R.string.genre)
            }
        }
    }

    private fun setupRecyclerView() {
        binding.rvFilterItems.layoutManager = LinearLayoutManager(requireContext())
        binding.rvFilterItems.adapter = searchChooseAdapter
    }

    private fun setupBackButton() {
        binding.ivBack.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
    }

    private fun setupSearchBar() {
        binding.sbMain.doOnTextChanged { query, _, _, _ ->
            viewModel.sendRequest(query.toString())
        }
        when (filterMode) {
            FilterMode.COUNTRY -> {
                binding.sbMain.hint = getString(R.string.input_country)
            }

            FilterMode.GENRE -> {
                binding.sbMain.hint = getString(R.string.input_genre)
            }
        }
    }

    private fun sendDataToPreviousFragment() {
        when (filterMode) {
            FilterMode.COUNTRY -> {
                parentFragmentManager.setFragmentResult(
                    SearchSettingsFragment.COUNTRY_MODE_KEY,
                    Bundle().apply {
                        putString(SearchSettingsFragment.COUNTRY_NAME_KEY, filterItem)
                    }
                )
                parentFragmentManager.popBackStack()
            }

            FilterMode.GENRE -> {
                parentFragmentManager.setFragmentResult(
                    SearchSettingsFragment.GENRE_MODE_KEY,
                    Bundle().apply {
                        putString(SearchSettingsFragment.GENRE_NAME_KEY, filterItem)
                    }
                )
                parentFragmentManager.popBackStack()
            }
        }
    }

    private fun setupButtonReset() {
        if (filterItem == getString(R.string.any) || filterItem == getString(R.string.any_v2)) {
            binding.buttonReset.visibility = View.GONE
        } else {
            binding.buttonReset.setOnClickListener {
                resetData()
            }
        }
    }

    private fun resetData() {
        when (filterMode) {
            FilterMode.COUNTRY -> {
                parentFragmentManager.setFragmentResult(
                    SearchSettingsFragment.COUNTRY_MODE_KEY,
                    Bundle().apply {
                        putString(
                            SearchSettingsFragment.COUNTRY_NAME_KEY,
                            getString(R.string.any_v2)
                        )
                    }
                )
                parentFragmentManager.popBackStack()
            }

            FilterMode.GENRE -> {
                parentFragmentManager.setFragmentResult(
                    SearchSettingsFragment.GENRE_MODE_KEY,
                    Bundle().apply {
                        putString(
                            SearchSettingsFragment.GENRE_NAME_KEY,
                            getString(R.string.any)
                        )
                    }
                )
                parentFragmentManager.popBackStack()
            }
        }
    }

    companion object {

        private const val FILTER_ITEM = "filter_item_key"
        private const val FILTER_MODE = "filter_mode_key"

        @JvmStatic
        fun newInstance(filterItem: String, filterMode: String) =
            SearchChooseFragment().apply {
                arguments = Bundle().apply {
                    putString(FILTER_ITEM, filterItem)
                    putString(FILTER_MODE, filterMode)
                }
            }
    }
}