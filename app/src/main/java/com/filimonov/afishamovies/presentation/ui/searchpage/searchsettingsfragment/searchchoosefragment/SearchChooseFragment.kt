package com.filimonov.afishamovies.presentation.ui.searchpage.searchsettingsfragment.searchchoosefragment

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
import com.filimonov.afishamovies.presentation.utils.ViewModelFactory
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchChooseFragment : Fragment() {

    private var chooseItemResId: Int = NOT_SELECTED_RES_ID
    private lateinit var filterMode: FilterMode

    private var _binding: FragmentSearchChooseBinding? = null

    private val binding: FragmentSearchChooseBinding
        get() = _binding ?: throw RuntimeException("FragmentSearchChooseBinding == null")

    private val component by lazy {
        (requireActivity().application as AfishaMoviesApp).component
            .searchPageComponent()
            .create()
            .createSearchChooseComponent()
            .create(filterMode, chooseItemResId)
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[SearchChooseViewModel::class.java]
    }

    private val searchChooseAdapter by lazy {
        SearchChooseAdapter(
            viewModel.chooseResId,
            onItemClick = { chooseFilterResId ->
                viewModel.chooseResId = chooseFilterResId
                sendDataToPreviousFragment()
            }
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseArgs()

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
                    CHOOSE_COUNTRY_MODE_KEY,
                    Bundle().apply {
                        putInt(CHOOSE_COUNTRY_NAME_KEY, viewModel.chooseResId)
                    }
                )
                parentFragmentManager.popBackStack()
            }

            FilterMode.GENRE -> {
                parentFragmentManager.setFragmentResult(
                    CHOOSE_GENRE_MODE_KEY,
                    Bundle().apply {
                        putInt(CHOOSE_GENRE_NAME_KEY, viewModel.chooseResId)
                    }
                )
                parentFragmentManager.popBackStack()
            }
        }
    }

    private fun setupButtonReset() {
        if (viewModel.chooseResId == NOT_SELECTED_RES_ID) {
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
                    CHOOSE_COUNTRY_MODE_KEY,
                    Bundle().apply {
                        putInt(
                            CHOOSE_COUNTRY_NAME_KEY,
                            R.string.any_v2
                        )
                    }
                )
                parentFragmentManager.popBackStack()
            }

            FilterMode.GENRE -> {
                parentFragmentManager.setFragmentResult(
                    CHOOSE_GENRE_MODE_KEY,
                    Bundle().apply {
                        putInt(
                            CHOOSE_GENRE_NAME_KEY,
                            R.string.any
                        )
                    }
                )
                parentFragmentManager.popBackStack()
            }
        }
    }

    private fun parseArgs() {
        arguments?.let {
            val chooseItemResIdBundle = it.getInt(CHOOSE_ITEM, NOT_SELECTED_RES_ID)
            chooseItemResId = chooseItemResIdBundle
            val filterModeBundle =
                it.getString(FILTER_MODE) ?: throw RuntimeException("param mode is absent")
            filterMode = FilterMode.valueOf(filterModeBundle)
        }
    }

    companion object {

        private const val NOT_SELECTED_RES_ID = -1

        private const val CHOOSE_ITEM = "choose_item_key"
        private const val FILTER_MODE = "filter_mode_key"

        const val CHOOSE_COUNTRY_MODE_KEY = "choose_country_mode_key"
        const val CHOOSE_COUNTRY_NAME_KEY = "choose_country_name_key"

        const val CHOOSE_GENRE_MODE_KEY = "choose_genre_mode_key"
        const val CHOOSE_GENRE_NAME_KEY = "choose_genre_name_key"

        @JvmStatic
        fun newInstance(chooseItem: Int, filterMode: String) =
            SearchChooseFragment().apply {
                arguments = Bundle().apply {
                    putInt(CHOOSE_ITEM, chooseItem)
                    putString(FILTER_MODE, filterMode)
                }
            }
    }
}