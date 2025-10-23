package com.filimonov.afishamovies.presentation.ui.searchpage.searchsettingsfragment

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.filimonov.afishamovies.R
import com.filimonov.afishamovies.databinding.FragmentSearchSettingsBinding
import com.filimonov.afishamovies.presentation.ui.searchpage.searchsettingsfragment.searchchoosedatafragment.SearchChooseDataFragment
import com.filimonov.afishamovies.presentation.ui.searchpage.searchsettingsfragment.searchchoosefragment.FilterMode
import com.filimonov.afishamovies.presentation.ui.searchpage.searchsettingsfragment.searchchoosefragment.SearchChooseFragment

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SearchSettingsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SearchSettingsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var _binding: FragmentSearchSettingsBinding? = null

    private val binding: FragmentSearchSettingsBinding
        get() = _binding ?: throw kotlin.RuntimeException("FragmentSearchSettingsBinding == null")

    private var showType: ShowType? = null
    private var sortType: SortType? = null
    private var country: String? = null
    private var genre: String? = null
    private var yearFrom: Int? = null
    private var yearTo: Int? = null
    private var ratingFrom: Int? = null
    private var ratingTo: Int? = null
    private var isDontWatched: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
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
        setupBackButton()
        setupMaterialButtonClickListeners()
        setupRangeSlider()
        setupLinearLayoutsClickListeners()
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
        binding.btnAll.setOnClickListener {
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

    private fun setupFragmentResultListeners() {
        parentFragmentManager.setFragmentResultListener(
            COUNTRY_MODE_KEY,
            viewLifecycleOwner
        ) { _, bundle ->
            country = bundle.getString(COUNTRY_NAME_KEY)
            binding.tvCountry.text = country
            if (country == getString(R.string.any_v2)) {
                country = null
            }
        }
        parentFragmentManager.setFragmentResultListener(
            GENRE_MODE_KEY,
            viewLifecycleOwner
        ) { _, bundle ->
            genre = bundle.getString(GENRE_NAME_KEY)
            binding.tvGenre.text = genre
            if (genre == getString(R.string.any)) {
                genre = null
            }
        }
        parentFragmentManager.setFragmentResultListener(
            YEAR_MODE_KEY,
            viewLifecycleOwner
        ) { _, bundle ->
            yearFrom = bundle.getInt(YEAR_FROM_NAME_KEY)
            yearTo = bundle.getInt(YEAR_TO_NAME_KEY)
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

    companion object {

        const val COUNTRY_MODE_KEY = "country_mode_key"
        const val COUNTRY_NAME_KEY = "country_name_key"

        const val GENRE_MODE_KEY = "genre_mode_key"
        const val GENRE_NAME_KEY = "genre_name_key"

        const val YEAR_MODE_KEY = "year_mode_key"
        const val YEAR_FROM_NAME_KEY = "year_from_name_key"
        const val YEAR_TO_NAME_KEY = "year_to_name_key"

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SearchSettingsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}