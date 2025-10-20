package com.filimonov.afishamovies.presentation.ui.searchpage.searchchoosedatafragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.filimonov.afishamovies.R
import com.filimonov.afishamovies.databinding.FragmentSearchChooseDataBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class SearchChooseDataFragment : Fragment() {
    private var _binding: FragmentSearchChooseDataBinding? = null

    private val binding: FragmentSearchChooseDataBinding
        get() = _binding ?: throw RuntimeException("FragmentSearchChooseDataBinding == null")

    private var selectedYearFrom: Int = UNDEFINED_YEAR_FROM
    private var selectedYearTo: Int = UNDEFINED_YEAR_TO

    private val adapterFrom: YearsAdapter by lazy {
        YearsAdapter(maxActiveYear = selectedYearTo, selectedYear = selectedYearFrom) {
            selectedYearFrom = it ?: UNDEFINED_YEAR_FROM
            adapterTo.setMinActiveYear(it)
            adapterFrom.updateSelectedYear(it)
            Log.d("AAA", it.toString())
        }
    }
    private val adapterTo: YearsAdapter by lazy {
        YearsAdapter(minActiveYear = selectedYearFrom, selectedYear = selectedYearTo) {
            selectedYearTo = it ?: UNDEFINED_YEAR_TO
            adapterFrom.setMaxActiveYear(it)
            adapterTo.updateSelectedYear(it)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            selectedYearFrom = it.getInt(SELECTED_YEAR_FROM_KEY)
            selectedYearTo = it.getInt(SELECTED_YEAR_TO_KEY)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchChooseDataBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setPaddingRootView()
        binding.rvFrom.layoutManager = GridLayoutManager(requireContext(), 3)
        binding.rvFrom.adapter = adapterFrom
        adapterFrom.submitList((1998..2009).toList())
        binding.rvTo.layoutManager = GridLayoutManager(requireContext(), 3)
        binding.rvTo.adapter = adapterTo
        adapterTo.submitList((1998..2009).toList())
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

        private const val SELECTED_YEAR_FROM_KEY = "selected_year_from"
        private const val SELECTED_YEAR_TO_KEY = "selected_year_to"
        private const val UNDEFINED_YEAR_FROM = Int.MIN_VALUE
        private const val UNDEFINED_YEAR_TO = Int.MAX_VALUE

        @JvmStatic
        fun newInstance(selectedYearFrom: Int?, selectedYearTo: Int?) =
            SearchChooseDataFragment().apply {
                arguments = Bundle().apply {
                    putInt(SELECTED_YEAR_FROM_KEY, selectedYearFrom ?: UNDEFINED_YEAR_FROM)
                    putInt(SELECTED_YEAR_TO_KEY, selectedYearTo ?: UNDEFINED_YEAR_TO)
                }
            }
    }
}