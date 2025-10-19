package com.filimonov.afishamovies.presentation.ui.searchpage

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
import com.filimonov.afishamovies.presentation.ui.searchpage.searchchoosefragment.FilterMode
import com.filimonov.afishamovies.presentation.ui.searchpage.searchchoosefragment.SearchChooseFragment

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
        setupRangeSlider()
        setupLinearLayoutsClickListeners()
        setupFragmentResultListeners()
    }

    private fun setupLinearLayoutsClickListeners() {
        binding.llDontWatch.setOnClickListener {
            it.isSelected = !it.isSelected
            if (it.isSelected) {
                animateBackgroundColor(
                    it,
                    Color.WHITE,
                    ContextCompat.getColor(requireContext(), R.color.selected_item)
                )
            } else {
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
                        binding.tvCountry.text.toString(),
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
                        binding.tvGenre.text.toString(),
                        FilterMode.GENRE.name
                    )
                )
                .commit()
        }
    }

    private fun setupFragmentResultListeners() {
        parentFragmentManager.setFragmentResultListener(
            COUNTRY_MODE_KEY,
            viewLifecycleOwner
        ) { _, bundle ->
            val selectedCountry = bundle.getString(COUNTRY_NAME_KEY)
            binding.tvCountry.text = selectedCountry
        }
        parentFragmentManager.setFragmentResultListener(
            GENRE_MODE_KEY,
            viewLifecycleOwner
        ) { _, bundle ->
            val selectedCountry = bundle.getString(GENRE_NAME_KEY)
            binding.tvGenre.text = selectedCountry
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
                val min = slider.values[0].toInt()
                val max = slider.values[1].toInt()
                val rating = if (min == 1 && max == 10) {
                    "Любой"
                } else if (min == max) {
                    "$min"
                } else {
                    "$min - $max"
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