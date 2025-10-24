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
import androidx.transition.Slide
import com.filimonov.afishamovies.R
import com.filimonov.afishamovies.databinding.FragmentSearchSettingsBinding
import com.filimonov.afishamovies.presentation.ui.MainActivity
import com.filimonov.afishamovies.presentation.ui.searchpage.searchsettingsfragment.searchchoosedatafragment.SearchChooseDataFragment
import com.filimonov.afishamovies.presentation.ui.searchpage.searchsettingsfragment.searchchoosefragment.FilterMode
import com.filimonov.afishamovies.presentation.ui.searchpage.searchsettingsfragment.searchchoosefragment.SearchChooseFragment

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
    private var country: String? = null
    private var genre: String? = null
    private var yearFrom: Int? = null
    private var yearTo: Int? = null
    private var ratingFrom: Int? = null
    private var ratingTo: Int? = null
    private var isDontWatched: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseArgs()
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
        setupBackButton()
        setupMaterialButtonClickListeners()
        setupButtonClickListeners()
        setupLinearLayoutsClickListeners()
        setupRangeSlider()
        setupFragmentResultListeners()
    }

    private fun setupLinearLayoutsClickListeners() {
        binding.llDontWatch.setOnClickListener {
            it.isSelected = !it.isSelected
            if (it.isSelected) {
                isDontWatched = true
                animateBackgroundColor(
                    it,
                    Color.WHITE,
                    ContextCompat.getColor(requireContext(), R.color.selected_item)
                )
            } else {
                isDontWatched = false
                animateBackgroundColor(
                    it,
                    ContextCompat.getColor(requireContext(), R.color.selected_item),
                    Color.WHITE
                )
            }
        }
        binding.llCountry.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .addToBackStack(null)
                .add(
                    R.id.fragment_container, SearchChooseFragment.newInstance(
                        country,
                        FilterMode.COUNTRY.name
                    )
                )
                .commit()
        }
        binding.llGenre.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .addToBackStack(null)
                .add(
                    R.id.fragment_container, SearchChooseFragment.newInstance(
                        genre,
                        FilterMode.GENRE.name
                    )
                )
                .commit()
        }
        binding.llPeriod.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .addToBackStack(null)
                .add(
                    R.id.fragment_container, SearchChooseDataFragment.newInstance(yearFrom, yearTo)
                )
                .commit()
        }
    }

    private fun setupMaterialButtonClickListeners() {
        binding.btnAll.setOnClickListener {
            showType = ShowType.ALL
        }
        binding.btnFilm.setOnClickListener {
            showType = ShowType.FILM
        }
        binding.btnSeries.setOnClickListener {
            showType = ShowType.SERIES
        }
        binding.btnDate.setOnClickListener {
            sortType = SortType.DATE
        }
        binding.btnPopular.setOnClickListener {
            sortType = SortType.POPULAR
        }
        binding.btnRating.setOnClickListener {
            sortType = SortType.RATING
        }
    }

    private fun setupButtonClickListeners() {
        binding.buttonReset.setOnClickListener {
            binding.tgShow.check(R.id.btn_all)
            showType = ShowType.ALL
            country = null
            binding.tvCountry.text = getString(R.string.any_v2)
            genre = null
            binding.tvGenre.text = getString(R.string.any)
            yearFrom = null
            yearTo = null
            binding.tvPeriod.text = getString(R.string.any)
            binding.rangeSlider.values = listOf(1f, 10f)
            ratingFrom = null
            ratingTo = null
            binding.tvRating.text = getString(R.string.any)
            binding.tgSort.check(R.id.btn_date)
            sortType = SortType.DATE
            isDontWatched = false
            binding.llDontWatch.isSelected = false
        }

        binding.buttonSubmit.setOnClickListener {
            sendSettingsToPreviousFragment()
            parentFragmentManager.popBackStack()
        }
    }

    private fun sendSettingsToPreviousFragment() {
        parentFragmentManager.setFragmentResult(
            SHOW_MODE_KEY,
            Bundle().apply {
                putString(SHOW_NAME_KEY, showType.name)
            }
        )
        parentFragmentManager.setFragmentResult(
            COUNTRY_MODE_KEY,
            Bundle().apply {
                putString(COUNTRY_NAME_KEY, country)
            }
        )
        parentFragmentManager.setFragmentResult(
            GENRE_MODE_KEY,
            Bundle().apply {
                putString(GENRE_NAME_KEY, genre)
            }
        )
        parentFragmentManager.setFragmentResult(
            YEAR_MODE_KEY,
            Bundle().apply {
                putInt(YEAR_FROM_NAME_KEY, yearFrom ?: ZERO_YEAR_VALUE)
                putInt(YEAR_TO_NAME_KEY, yearTo ?: ZERO_YEAR_VALUE)
            }
        )
        parentFragmentManager.setFragmentResult(
            RATING_MODE_KEY,
            Bundle().apply {
                putInt(RATING_FROM_NAME_KEY, ratingFrom ?: RATING_FROM_DEFAULT)
                putInt(RATING_TO_NAME_KEY, ratingTo ?: RATING_TO_DEFAULT)
            }
        )
        parentFragmentManager.setFragmentResult(
            SORT_MODE_KEY,
            Bundle().apply {
                putString(SORT_NAME_KEY, sortType.name)
            }
        )
        parentFragmentManager.setFragmentResult(
            IS_DONT_WATCHED_MODE_KEY,
            Bundle().apply {
                putBoolean(IS_DONT_WATCHED_NAME_KEY, isDontWatched)
            }
        )
    }

    private fun setupFragmentResultListeners() {
        parentFragmentManager.setFragmentResultListener(
            SearchChooseFragment.CHOOSE_COUNTRY_MODE_KEY,
            viewLifecycleOwner
        ) { _, bundle ->
            country = bundle.getString(SearchChooseFragment.CHOOSE_COUNTRY_NAME_KEY)
            binding.tvCountry.text = country
            if (country == getString(R.string.any_v2)) {
                country = null
            }
        }
        parentFragmentManager.setFragmentResultListener(
            SearchChooseFragment.CHOOSE_GENRE_MODE_KEY,
            viewLifecycleOwner
        ) { _, bundle ->
            genre = bundle.getString(SearchChooseFragment.CHOOSE_GENRE_NAME_KEY)
            binding.tvGenre.text = genre
            if (genre == getString(R.string.any)) {
                genre = null
            }
        }
        parentFragmentManager.setFragmentResultListener(
            SearchChooseDataFragment.CHOOSE_YEAR_MODE_KEY,
            viewLifecycleOwner
        ) { _, bundle ->
            yearFrom = bundle.getInt(SearchChooseDataFragment.CHOOSE_YEAR_FROM_NAME_KEY)
            yearTo = bundle.getInt(SearchChooseDataFragment.CHOOSE_YEAR_TO_NAME_KEY)
            if (yearFrom != Int.MIN_VALUE && yearTo != Int.MAX_VALUE && yearFrom != yearTo) {
                binding.tvPeriod.text = String.format("с %s до %s", yearFrom, yearTo)
            } else if (yearFrom == yearTo) {
                binding.tvPeriod.text = yearFrom.toString()
            } else {
                yearFrom = null
                yearTo = null
                binding.tvPeriod.text = getString(R.string.any)
            }
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
                ratingFrom = slider.values[0].toInt()
                ratingTo = slider.values[1].toInt()
                val rating = if (ratingFrom == 1 && ratingTo == 10) {
                    getString(R.string.any)
                } else if (ratingFrom == ratingTo) {
                    "$ratingFrom"
                } else {
                    "$ratingFrom - $ratingTo"
                }
                binding.tvRating.text = rating
            }
        }
    }

    private fun setupBackButton() {
        binding.ivBack.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
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
            country = it.getString(COUNTRY_KEY)
            genre = it.getString(GENRE_KEY)
            yearFrom = it.getInt(YEAR_FROM_KEY)
            yearTo = it.getInt(YEAR_TO_KEY)
            ratingFrom = it.getInt(RATING_FROM_KEY)
            ratingTo = it.getInt(RATING_TO_KEY)
            sortType = SortType.valueOf(it.getString(SORT_TYPE_KEY, SortType.DATE.name))
            isDontWatched = it.getBoolean(IS_DONT_WATCH_KEY)
        }
    }

    companion object {
        const val SHOW_MODE_KEY = "show_mode_key"
        const val SHOW_NAME_KEY = "show_name_key"

        const val COUNTRY_MODE_KEY = "country_mode_key"
        const val COUNTRY_NAME_KEY = "country_name_key"

        const val GENRE_MODE_KEY = "genre_mode_key"
        const val GENRE_NAME_KEY = "genre_name_key"

        const val YEAR_MODE_KEY = "year_mode_key"
        const val YEAR_FROM_NAME_KEY = "year_from_name_key"
        const val YEAR_TO_NAME_KEY = "year_to_name_key"

        const val RATING_MODE_KEY = "rating_mode_key"
        const val RATING_FROM_NAME_KEY = "rating_from_name_key"
        const val RATING_TO_NAME_KEY = "rating_to_name_key"

        const val SORT_MODE_KEY = "sort_mode_key"
        const val SORT_NAME_KEY = "sort_name_key"

        const val IS_DONT_WATCHED_MODE_KEY = "is_dont_watched_mode_key"
        const val IS_DONT_WATCHED_NAME_KEY = "is_dont_watched_name_key"

        private const val ZERO_YEAR_VALUE = 0
        private const val RATING_FROM_DEFAULT = 1
        private const val RATING_TO_DEFAULT = 10

        @JvmStatic
        fun newInstance(
            showType: String?,
            country: String?,
            genre: String?,
            yearFrom: Int?,
            yearTo: Int?,
            ratingFrom: Int?,
            ratingTo: Int?,
            sortType: String?,
            isDontWatched: Boolean
        ) =
            SearchSettingsFragment().apply {
                arguments = Bundle().apply {
                    putString(SHOW_TYPE_KEY, showType)
                    putString(COUNTRY_KEY, country)
                    putString(GENRE_KEY, genre)
                    putInt(YEAR_FROM_KEY, yearFrom ?: ZERO_YEAR_VALUE)
                    putInt(YEAR_TO_KEY, yearTo ?: ZERO_YEAR_VALUE)
                    putInt(RATING_FROM_KEY, ratingFrom ?: RATING_FROM_DEFAULT)
                    putInt(RATING_TO_KEY, ratingTo ?: RATING_TO_DEFAULT)
                    putString(SORT_TYPE_KEY, sortType)
                    putBoolean(IS_DONT_WATCH_KEY, isDontWatched)
                }
            }
    }
}