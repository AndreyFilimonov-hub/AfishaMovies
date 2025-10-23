package com.filimonov.afishamovies.presentation.ui.searchpage.searchchoosedatafragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.filimonov.afishamovies.AfishaMoviesApp
import com.filimonov.afishamovies.databinding.FragmentSearchChooseDataBinding
import com.filimonov.afishamovies.presentation.ui.MainActivity
import com.filimonov.afishamovies.presentation.ui.searchpage.SearchSettingsFragment
import com.filimonov.afishamovies.presentation.utils.ViewModelFactory
import javax.inject.Inject

class SearchChooseDataFragment : Fragment() {
    private var _binding: FragmentSearchChooseDataBinding? = null

    private val binding: FragmentSearchChooseDataBinding
        get() = _binding ?: throw RuntimeException("FragmentSearchChooseDataBinding == null")

    private var selectedYearFrom: Int? = null
    private var selectedYearTo: Int? = null

    private val adapterFrom: YearsAdapter by lazy {
        YearsAdapter(
            maxActiveYear = selectedYearTo ?: UNDEFINED_YEAR_TO,
            selectedYear = selectedYearFrom
        ) {
            selectedYearFrom = it
            checkButtonEnable()
            adapterTo.setMinActiveYear(it)
            adapterFrom.updateSelectedYear(it)
        }
    }

    private val adapterTo: YearsAdapter by lazy {
        YearsAdapter(
            minActiveYear = selectedYearFrom ?: UNDEFINED_YEAR_FROM,
            selectedYear = selectedYearTo
        ) {
            selectedYearTo = it
            checkButtonEnable()
            adapterFrom.setMaxActiveYear(it)
            adapterTo.updateSelectedYear(it)
        }
    }

    private val component by lazy {
        (requireActivity().application as AfishaMoviesApp).component
            .searchPageComponent()
            .create()
            .createSearchChooseDateComponent()
            .create(selectedYearFrom, selectedYearTo)
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[SearchChooseDataViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseArgs()
        component.inject(this)
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
        checkButtonEnable()
        offBottomNav()
        setupRecyclerView()
        setupClickListeners()
        observeViewModel()
    }

    private fun setupRecyclerView() {
        binding.rvFrom.layoutManager = GridLayoutManager(requireContext(), 3)
        binding.rvFrom.adapter = adapterFrom
        binding.rvTo.layoutManager = GridLayoutManager(requireContext(), 3)
        binding.rvTo.adapter = adapterTo
    }

    private fun setupClickListeners() {
        binding.ivBackFrom.setOnClickListener {
            viewModel.previousStep(DateRangeType.FROM)
        }
        binding.ivNextFrom.setOnClickListener {
            viewModel.nextStep(DateRangeType.FROM)
        }
        binding.ivBackTo.setOnClickListener {
            viewModel.previousStep(DateRangeType.TO)
        }
        binding.ivNextTo.setOnClickListener {
            viewModel.nextStep(DateRangeType.TO)
        }
        binding.buttonPick.setOnClickListener {// check param selectedYearFrom and selectedYearTo
            parentFragmentManager.setFragmentResult(
                SearchSettingsFragment.YEAR_MODE_KEY,
                Bundle().apply {
                    putInt(
                        SearchSettingsFragment.YEAR_FROM_NAME_KEY,
                        selectedYearFrom ?: Int.MIN_VALUE
                    )
                    putInt(SearchSettingsFragment.YEAR_TO_NAME_KEY, selectedYearTo ?: Int.MAX_VALUE)
                }
            )
            parentFragmentManager.popBackStack()
        }
    }

    private fun checkButtonEnable() {
        with(binding.buttonPick) {
            if (!(selectedYearFrom == null && selectedYearTo != null || selectedYearFrom != null && selectedYearTo == null)) {
                isEnabled = true
            } else {
                isEnabled = false
            }
        }
    }

    private fun observeViewModel() {
        viewModel.yearsFromLd.observe(viewLifecycleOwner) {
            adapterFrom.submitList(it)
            binding.tvPeriodFrom.text = String.format("%s - %s", it.first(), it.last())
        }
        viewModel.yearsToLd.observe(viewLifecycleOwner) {
            adapterTo.submitList(it)
            binding.tvPeriodTo.text = String.format("%s - %s", it.first(), it.last())
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

    private fun parseArgs() {
        arguments?.let {
            val selectedYearFromBundle = it.getInt(SELECTED_YEAR_FROM_KEY)
            selectedYearFrom = if (selectedYearFromBundle == UNDEFINED_YEAR_FROM) {
                null
            } else {
                selectedYearFromBundle
            }

            val selectedYearToBundle = it.getInt(SELECTED_YEAR_TO_KEY)
            selectedYearTo = if (selectedYearToBundle == UNDEFINED_YEAR_TO) {
                null
            } else {
                selectedYearToBundle
            }
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