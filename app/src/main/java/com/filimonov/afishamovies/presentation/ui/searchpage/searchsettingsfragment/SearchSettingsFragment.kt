package com.filimonov.afishamovies.presentation.ui.searchpage.searchsettingsfragment

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.transition.Slide
import com.filimonov.afishamovies.AfishaMoviesApp
import com.filimonov.afishamovies.R
import com.filimonov.afishamovies.databinding.FragmentSearchSettingsBinding
import com.filimonov.afishamovies.presentation.ui.MainActivity
import com.filimonov.afishamovies.presentation.ui.searchpage.ShowType
import com.filimonov.afishamovies.presentation.ui.searchpage.SortType
import com.filimonov.afishamovies.presentation.ui.searchpage.searchsettingsfragment.searchchoosedatafragment.SearchChooseDataFragment
import com.filimonov.afishamovies.presentation.ui.searchpage.searchsettingsfragment.searchchoosefragment.Countries
import com.filimonov.afishamovies.presentation.ui.searchpage.searchsettingsfragment.searchchoosefragment.FilterMode
import com.filimonov.afishamovies.presentation.ui.searchpage.searchsettingsfragment.searchchoosefragment.Genres
import com.filimonov.afishamovies.presentation.ui.searchpage.searchsettingsfragment.searchchoosefragment.SearchChooseFragment
import com.filimonov.afishamovies.presentation.utils.ViewModelFactory
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val SHOW_TYPE_KEY = "show_type_key"
private const val COUNTRY_KEY = "country_key"
private const val GENRE_KEY = "genre_key"
private const val YEAR_FROM_KEY = "year_from_key"
private const val YEAR_TO_KEY = "year_to_key"
private const val RATING_FROM_KEY = "rating_from_key"
private const val RATING_TO_KEY = "rating_to_key"
private const val SORT_TYPE_KEY = "sort_type_key"
private const val IS_DONT_WATCH_KEY = "is_dont_watch_key"

class SearchSettingsFragment : Fragment() {

    private var _binding: FragmentSearchSettingsBinding? = null

    private val binding: FragmentSearchSettingsBinding
        get() = _binding ?: throw RuntimeException("FragmentSearchSettingsBinding == null")

    private lateinit var showType: ShowType
    private lateinit var sortType: SortType
    private var countryResId: Int = R.string.any_v2
    private var genreResId: Int = R.string.any
    private var yearFrom: Int = YEAR_FROM_DEFAULT
    private var yearTo: Int = YEAR_TO_DEFAULT
    private var ratingFrom: Float = RATING_FROM_DEFAULT
    private var ratingTo: Float = RATING_TO_DEFAULT
    private var isDontWatched: Boolean = false

    private var previousState: SearchSettingsState.Success? = null

    private val component by lazy {
        (requireActivity().application as AfishaMoviesApp).component
            .searchPageComponent()
            .create()
            .createSearchSettingsComponent()
            .create(
                showType,
                sortType,
                requireContext().getString(countryResId),
                requireContext().getString(genreResId),
                yearFrom,
                yearTo,
                ratingFrom,
                ratingTo,
                isDontWatched
            )
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[SearchSettingsViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseArgs()
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
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchSettingsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        offBottomNav()
        setupRangeSlider()
        setupFragmentResultListeners()
        observeViewModel()
        setupBackButton()
        setupMaterialButtonClickListeners()
        setupButtonClickListeners()
        setupLinearLayoutsClickListeners()
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.state.collect { state ->
                    when (state) {
                        is SearchSettingsState.Success -> {
                            setupTextViews(previousState, state)
                            previousState = state
                        }
                    }
                }
            }
        }
    }

    private fun setupTextViews(
        old: SearchSettingsState.Success?,
        new: SearchSettingsState.Success
    ) {

        if (old == null) {
            initSetupTextViews(new)
            return
        }

        if (old.showType != new.showType) {
            when (new.showType) {
                ShowType.ALL -> binding.tgShow.check(R.id.btn_all)
                ShowType.FILM -> binding.tgShow.check(R.id.btn_film)
                ShowType.SERIES -> binding.tgShow.check(R.id.btn_series)
            }
        }

        if (old.country != new.country) {
            binding.tvCountry.text = new.country ?: requireContext().getString(R.string.any_v2)
        }

        if (old.genre != new.genre) {
            binding.tvGenre.text = new.genre ?: requireContext().getString(R.string.any)
        }

        if (old.yearRange != new.yearRange) {
            binding.tvYearRange.text = new.yearRange
        }

        if (old.ratingValues != new.ratingValues) {
            binding.rangeSlider.values = new.ratingValues
        }

        if (old.ratingRange != new.ratingRange) {
            binding.tvRatingRange.text = new.ratingRange
        }

        if (old.sortType != new.sortType) {
            when (new.sortType) {
                SortType.DATE -> binding.tgSort.check(R.id.btn_date)
                SortType.POPULAR -> binding.tgSort.check(R.id.btn_popular)
                SortType.RATING -> binding.tgSort.check(R.id.btn_rating)
            }
        }

        if (old.isDontWatched != new.isDontWatched) {
            if (new.isDontWatched) {
                binding.llDontWatch.isSelected = true
                animateBackgroundColor(
                    binding.llDontWatch,
                    Color.WHITE,
                    ContextCompat.getColor(requireContext(), R.color.selected_item)
                )
            } else {
                binding.llDontWatch.isSelected = false
                animateBackgroundColor(
                    binding.llDontWatch,
                    ContextCompat.getColor(requireContext(), R.color.selected_item),
                    Color.WHITE
                )
            }
        }
    }

    private fun initSetupTextViews(state: SearchSettingsState.Success) {
        with(state) {
            when (showType) {
                ShowType.ALL -> binding.tgShow.check(R.id.btn_all)
                ShowType.FILM -> binding.tgShow.check(R.id.btn_film)
                ShowType.SERIES -> binding.tgShow.check(R.id.btn_series)
            }
            binding.tvCountry.text = country ?: requireContext().getString(R.string.any_v2)
            binding.tvGenre.text = genre ?: requireContext().getString(R.string.any)
            binding.tvYearRange.text = yearRange
            binding.rangeSlider.values = ratingValues
            binding.tvRatingRange.text = ratingRange
            when (sortType) {
                SortType.DATE -> binding.tgSort.check(R.id.btn_date)
                SortType.POPULAR -> binding.tgSort.check(R.id.btn_popular)
                SortType.RATING -> binding.tgSort.check(R.id.btn_rating)
            }
            if (isDontWatched) {
                binding.llDontWatch.isSelected = true
                animateBackgroundColor(
                    binding.llDontWatch,
                    Color.WHITE,
                    ContextCompat.getColor(requireContext(), R.color.selected_item)
                )
            } else {
                with(binding.llDontWatch) {
                    isSelected = false
                    setBackgroundColor(Color.WHITE)
                }
            }
        }
    }

    private fun setupLinearLayoutsClickListeners() {
        binding.llDontWatch.setOnClickListener {
            viewModel.updateIsDontWatched(!viewModel.isDontWatched)
        }
        binding.llCountry.setOnClickListener {
            val searchChooseCountryFragment = SearchChooseFragment.newInstance(
                viewModel.country,
                FilterMode.COUNTRY.name
            )
            (requireActivity() as MainActivity).openFragment(searchChooseCountryFragment)
        }
        binding.llGenre.setOnClickListener {
            val searchChooseGenreFragment = SearchChooseFragment.newInstance(
                viewModel.genre,
                FilterMode.GENRE.name
            )
            (requireActivity() as MainActivity).openFragment(searchChooseGenreFragment)
        }
        binding.llPeriod.setOnClickListener {
            val searchChooseDataFragment = SearchChooseDataFragment.newInstance(yearFrom, yearTo)
            (requireActivity() as MainActivity).openFragment(searchChooseDataFragment)
        }
    }

    private fun setupMaterialButtonClickListeners() {
        binding.btnAll.setOnClickListener {
            viewModel.updateShowType(ShowType.ALL)
        }
        binding.btnFilm.setOnClickListener {
            viewModel.updateShowType(ShowType.FILM)
        }
        binding.btnSeries.setOnClickListener {
            viewModel.updateShowType(ShowType.SERIES)
        }
        binding.btnDate.setOnClickListener {
            viewModel.updateSortType(SortType.DATE)
        }
        binding.btnPopular.setOnClickListener {
            viewModel.updateSortType(SortType.POPULAR)
        }
        binding.btnRating.setOnClickListener {
            viewModel.updateSortType(SortType.RATING)
        }
    }

    private fun setupButtonClickListeners() {
        binding.buttonReset.setOnClickListener {
            viewModel.reset()
        }

        binding.buttonSubmit.setOnClickListener {
            sendSettingsToPreviousFragment()
            parentFragmentManager.popBackStack()
        }
    }

    private fun sendSettingsToPreviousFragment() {
        parentFragmentManager.setFragmentResult(
            FILTERS_KEY,
            Bundle().apply {
                putString(SHOW_NAME_KEY, viewModel.showType.name)
                putString(COUNTRY_NAME_KEY, viewModel.country)
                putString(GENRE_NAME_KEY, viewModel.genre)
                putInt(YEAR_FROM_NAME_KEY, viewModel.yearFrom)
                putInt(YEAR_TO_NAME_KEY, viewModel.yearTo)
                putFloat(RATING_FROM_NAME_KEY, viewModel.ratingFrom)
                putFloat(RATING_TO_NAME_KEY, viewModel.ratingTo)
                putString(SORT_NAME_KEY, viewModel.sortType.name)
                putBoolean(IS_DONT_WATCHED_NAME_KEY, viewModel.isDontWatched)
            }
        )
    }

    private fun setupFragmentResultListeners() {
        parentFragmentManager.setFragmentResultListener(
            SearchChooseFragment.CHOOSE_COUNTRY_MODE_KEY,
            viewLifecycleOwner
        ) { _, bundle ->
            val country = bundle.getString(SearchChooseFragment.CHOOSE_COUNTRY_NAME_KEY)
                ?: requireContext().getString(R.string.any_v2)
            viewModel.updateCountry(country)
        }
        parentFragmentManager.setFragmentResultListener(
            SearchChooseFragment.CHOOSE_GENRE_MODE_KEY,
            viewLifecycleOwner
        ) { _, bundle ->
            val genre = bundle.getString(SearchChooseFragment.CHOOSE_GENRE_NAME_KEY)
                ?: requireContext().getString(R.string.any)
            viewModel.updateGenre(genre)
        }
        parentFragmentManager.setFragmentResultListener(
            SearchChooseDataFragment.CHOOSE_YEAR_MODE_KEY,
            viewLifecycleOwner
        ) { _, bundle ->
            yearFrom = bundle.getInt(SearchChooseDataFragment.CHOOSE_YEAR_FROM_NAME_KEY)
            yearTo = bundle.getInt(SearchChooseDataFragment.CHOOSE_YEAR_TO_NAME_KEY)
            viewModel.updateYearRange(yearFrom, yearTo)
        }
    }

    private fun setupRangeSlider() {
        with(binding.rangeSlider) {
            val drawable = ContextCompat.getDrawable(requireContext(), R.drawable.thumb_circle)
                ?: throw RuntimeException()
            thumbRadius = (resources.displayMetrics.density * 12).toInt()
            setCustomThumbDrawable(drawable)
            trackActiveTintList =
                ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.blue))
            trackInactiveTintList = ColorStateList.valueOf(
                ContextCompat.getColor(
                    requireContext(),
                    android.R.color.darker_gray
                )
            )
            addOnChangeListener { slider, _, _ ->
                val ratingFrom = slider.values[0].toFloat()
                val ratingTo = slider.values[1].toFloat()
                viewModel.updateRangeSlider(ratingFrom, ratingTo)
            }
        }
    }

    private fun setupBackButton() {
        binding.ivBack.setOnClickListener {
            (requireActivity() as MainActivity).closeFragment(this)
        }
    }

    private fun offBottomNav() {
        val bNav = (requireActivity() as MainActivity).binging.bNav
        bNav.animate()
            .translationY(bNav.height.toFloat())
            .setDuration(1000)
            .withEndAction {
                bNav.visibility = View.GONE
            }
            .start()
    }

    private fun animateBackgroundColor(
        view: View,
        fromColor: Int,
        toColor: Int,
        duration: Long = 300
    ) {
        val colorAnimation = ValueAnimator.ofObject(ArgbEvaluator(), fromColor, toColor)
        colorAnimation.duration = duration
        colorAnimation.addUpdateListener { animator ->
            view.setBackgroundColor(animator.animatedValue as Int)
        }
        colorAnimation.start()
    }

    private fun parseArgs() {
        arguments?.let {
            showType = ShowType.valueOf(it.getString(SHOW_TYPE_KEY, ShowType.ALL.name))
            val country = Countries.entries.firstOrNull { value ->
                requireContext().getString(value.itemResId) == it.getString(COUNTRY_KEY)
            }
            val genre = Genres.entries.firstOrNull { value ->
                requireContext().getString(value.itemResId) == it.getString(GENRE_KEY)
            }
            countryResId = country?.itemResId ?: R.string.any_v2
            genreResId = genre?.itemResId ?: R.string.any
            yearFrom = it.getInt(YEAR_FROM_KEY)
            yearTo = it.getInt(YEAR_TO_KEY)
            ratingFrom = it.getFloat(RATING_FROM_KEY)
            ratingTo = it.getFloat(RATING_TO_KEY)
            sortType = SortType.valueOf(it.getString(SORT_TYPE_KEY, SortType.DATE.name))
            isDontWatched = it.getBoolean(IS_DONT_WATCH_KEY)
        }
    }

    companion object {
        const val FILTERS_KEY = "filters_key"

        const val SHOW_NAME_KEY = "show_name_key"

        const val COUNTRY_NAME_KEY = "country_name_key"

        const val GENRE_NAME_KEY = "genre_name_key"

        const val YEAR_FROM_NAME_KEY = "year_from_name_key"
        const val YEAR_TO_NAME_KEY = "year_to_name_key"

        const val RATING_FROM_NAME_KEY = "rating_from_name_key"
        const val RATING_TO_NAME_KEY = "rating_to_name_key"

        const val SORT_NAME_KEY = "sort_name_key"

        const val IS_DONT_WATCHED_NAME_KEY = "is_dont_watched_name_key"

        private const val YEAR_FROM_DEFAULT = Int.MIN_VALUE
        private const val YEAR_TO_DEFAULT = Int.MAX_VALUE
        private const val RATING_FROM_DEFAULT = 1f
        private const val RATING_TO_DEFAULT = 10f

        @JvmStatic
        fun newInstance(
            showType: String?,
            country: String?,
            genre: String?,
            yearFrom: Int?,
            yearTo: Int?,
            ratingFrom: Float?,
            ratingTo: Float?,
            sortType: String?,
            isDontWatched: Boolean
        ) =
            SearchSettingsFragment().apply {
                arguments = Bundle().apply {
                    putString(SHOW_TYPE_KEY, showType)
                    putString(COUNTRY_KEY, country)
                    putString(GENRE_KEY, genre)
                    putInt(YEAR_FROM_KEY, yearFrom ?: YEAR_FROM_DEFAULT)
                    putInt(YEAR_TO_KEY, yearTo ?: YEAR_TO_DEFAULT)
                    putFloat(RATING_FROM_KEY, ratingFrom ?: RATING_FROM_DEFAULT)
                    putFloat(RATING_TO_KEY, ratingTo ?: RATING_TO_DEFAULT)
                    putString(SORT_TYPE_KEY, sortType)
                    putBoolean(IS_DONT_WATCH_KEY, isDontWatched)
                }
            }
    }
}