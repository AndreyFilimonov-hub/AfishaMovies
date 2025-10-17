package com.filimonov.afishamovies.presentation.ui.searchpage.searchchoosefragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.Fade
import com.filimonov.afishamovies.R
import com.filimonov.afishamovies.databinding.FragmentSearchChooseBinding
import com.filimonov.afishamovies.presentation.ui.searchpage.SearchSettingsFragment

class SearchChooseFragment : Fragment() {

    private var _binding: FragmentSearchChooseBinding? = null

    private val binding: FragmentSearchChooseBinding
        get() = _binding ?: throw RuntimeException("FragmentSearchChooseBinding == null")

    private val viewModel by lazy {
        ViewModelProvider(this)[SearchChooseViewModel::class.java]
    }

    private var filterItem: String? = null
    private lateinit var filterMode: FilterMode

    private val searchChooseAdapter by lazy {
        SearchChooseAdapter(
            filterItem,
            onItemClick = { chooseFilterItem ->
                filterItem = chooseFilterItem
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
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            filterItem = it.getString(FILTER_ITEM)
            val filterModeBundle = it.getString(FILTER_MODE) ?: ""
            filterMode = FilterMode.valueOf(filterModeBundle)
        }
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
        setupButtonReset()
        setupSearchBar()
        setupRecyclerView()
        setupBackButton()
    }

    private fun setupRecyclerView() {
        binding.rvFilterItems.layoutManager = LinearLayoutManager(requireContext())
        binding.rvFilterItems.adapter = searchChooseAdapter
        when (filterMode) {
            FilterMode.COUNTRY -> {
                binding.tvTitle.text = getString(R.string.country)
                searchChooseAdapter.submitList(viewModel.getListOfCountries())
            }

            FilterMode.GENRE -> {
                binding.tvTitle.text = getString(R.string.genre)
                searchChooseAdapter.submitList(viewModel.getListOfGenres())
            }
        }
    }

    private fun setupBackButton() {
        binding.ivBack.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
    }

    private fun setupSearchBar() {
        when (filterMode) {
            FilterMode.COUNTRY -> {
                binding.sbMain.hint = getString(R.string.input_country)
            }
            FilterMode.GENRE -> {
                binding.sbMain.hint = getString(R.string.input_genre)
            }
        }
    }

    private fun setupButtonReset() {
        if (filterItem == getString(R.string.any) || filterItem == getString(R.string.any_v2)) {
            binding.buttonReset.isEnabled = false
        } else {
            binding.buttonReset.isEnabled = true
            binding.buttonReset.backgroundTintList =
                ContextCompat.getColorStateList(requireContext(), R.color.blue)
            binding.buttonReset.setOnClickListener {
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