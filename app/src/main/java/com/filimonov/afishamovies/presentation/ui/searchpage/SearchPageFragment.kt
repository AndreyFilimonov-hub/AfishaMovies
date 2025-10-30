package com.filimonov.afishamovies.presentation.ui.searchpage

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnNextLayout
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
import com.filimonov.afishamovies.presentation.ui.filmpage.FilmPageFragment
import com.filimonov.afishamovies.presentation.ui.filmpage.FilmPageMode
import com.filimonov.afishamovies.presentation.ui.searchpage.searchpageadapter.SearchItemAdapter
import com.filimonov.afishamovies.presentation.ui.searchpage.searchsettingsfragment.SearchSettingsFragment
import com.filimonov.afishamovies.presentation.utils.ViewModelFactory
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchPageFragment : Fragment() {

    private var _binding: FragmentSearchPageBinding? = null

    private val binding: FragmentSearchPageBinding
        get() = _binding ?: throw RuntimeException("FragmentSearchPageBinding == null")

    private var showType: ShowType = ShowType.ALL
    private var sortType: SortType = SortType.DATE
    private var country: String? = null
    private var genre: String? = null
    private var yearFrom: Int? = null
    private var yearTo: Int? = null
    private var ratingFrom: Float? = null
    private var ratingTo: Float? = null
    private var isDontWatched: Boolean = false

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
            requireActivity().supportFragmentManager.beginTransaction()
                .addToBackStack(null)
                .add(
                    R.id.fragment_container,
                    FilmPageFragment.newInstance(it.id, FilmPageMode.DEFAULT.name)
                )
                .commit()
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
        setupFragmentResultListeners()
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
                            viewModel.setupFilters(
                                showType,
                                country,
                                genre,
                                yearFrom,
                                yearTo,
                                ratingFrom,
                                ratingTo,
                                sortType,
                                isDontWatched
                            )
                        }

                        SearchPageState.Loading -> {

                        }

                        is SearchPageState.Success -> {
//                            Log.d("AAA", state.result.toString())
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
            parentFragmentManager.beginTransaction()
                .add(
                    R.id.fragment_container,
                    SearchSettingsFragment.newInstance(
                        showType.name,
                        country,
                        genre,
                        yearFrom,
                        yearTo,
                        ratingFrom,
                        ratingTo,
                        sortType.name,
                        isDontWatched
                    )
                )
                .addToBackStack(null)
                .commit()
        }
        binding.sbMain.doOnTextChanged { query, _, _, _ ->
            viewModel.sendRequest(query.toString())
        }
    }

    private fun setupFragmentResultListeners() {
        parentFragmentManager.setFragmentResultListener(
            SearchSettingsFragment.FILTERS_KEY,
            viewLifecycleOwner
        ) { _, bundle ->
            showType = ShowType.valueOf(
                bundle.getString(
                    SearchSettingsFragment.SHOW_NAME_KEY,
                    ShowType.ALL.name
                )
            )

            country = bundle.getString(SearchSettingsFragment.COUNTRY_NAME_KEY)
            genre = bundle.getString(SearchSettingsFragment.GENRE_NAME_KEY)

            val yearFromBundle = bundle.getInt(SearchSettingsFragment.YEAR_FROM_NAME_KEY)
            yearFrom = if (yearFromBundle == Int.MIN_VALUE) {
                null
            } else {
                yearFromBundle
            }
            val yearToBundle = bundle.getInt(SearchSettingsFragment.YEAR_TO_NAME_KEY)
            yearTo = if (yearToBundle == Int.MAX_VALUE) {
                null
            } else {
                yearToBundle
            }

            ratingFrom = bundle.getFloat(SearchSettingsFragment.RATING_FROM_NAME_KEY)
            ratingTo = bundle.getFloat(SearchSettingsFragment.RATING_TO_NAME_KEY)

            sortType = SortType.valueOf(
                bundle.getString(
                    SearchSettingsFragment.SORT_NAME_KEY,
                    SortType.DATE.name
                )
            )

            isDontWatched = bundle.getBoolean(SearchSettingsFragment.IS_DONT_WATCHED_NAME_KEY)

            setupFilters()
        }
    }

    private fun setupFilters() {
        viewModel.setupFilters(
            showType,
            country,
            genre,
            yearFrom,
            yearTo,
            ratingFrom,
            ratingTo,
            sortType,
            isDontWatched
        )

        viewModel.updateList()

        binding.rvReplySearch.doOnNextLayout {
            binding.rvReplySearch.scrollToPosition(0)
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