package com.filimonov.afishamovies.presentation.ui.searchpage.searchsettingsfragment.searchchoosedatafragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.transition.Fade
import com.filimonov.afishamovies.AfishaMoviesApp
import com.filimonov.afishamovies.databinding.FragmentSearchChooseDataBinding
import com.filimonov.afishamovies.presentation.ui.MainActivity
import com.filimonov.afishamovies.presentation.utils.ViewModelFactory
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchChooseDataFragment : Fragment() {

    companion object {

        const val CHOOSE_YEAR_MODE_KEY = "choose_year_mode_key"
        const val CHOOSE_YEAR_FROM_NAME_KEY = "choose_year_from_name_key"
        const val CHOOSE_YEAR_TO_NAME_KEY = "choose_year_to_name_key"

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

    private var _binding: FragmentSearchChooseDataBinding? = null
    private val binding: FragmentSearchChooseDataBinding
        get() = _binding ?: throw RuntimeException("FragmentSearchChooseDataBinding == null")

    private var selectedYearFrom: Int? = null
    private var selectedYearTo: Int? = null

    private val adapterFrom: YearsAdapter by lazy {
        YearsAdapter(
            maxActiveYear = viewModel.selectedYearTo ?: UNDEFINED_YEAR_TO,
            selectedYear = viewModel.selectedYearFrom
        ) {
            viewModel.selectedYearFrom = it
            checkButtonEnable()
            adapterTo.setMinActiveYear(it)
            adapterFrom.updateSelectedYear(it)
        }
    }

    private val adapterTo: YearsAdapter by lazy {
        YearsAdapter(
            minActiveYear = viewModel.selectedYearFrom ?: UNDEFINED_YEAR_FROM,
            selectedYear = viewModel.selectedYearTo
        ) {
            viewModel.selectedYearTo = it
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
        enterTransition = Fade()
        exitTransition = Fade()
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
        setInsets()
        checkButtonEnable()
        setupBackButton()
        setupRecyclerView()
        setupClickListeners()
        observeViewModel()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val safeInsets =
                insets.getInsets(WindowInsetsCompat.Type.systemBars() or WindowInsetsCompat.Type.displayCutout())
            v.updatePadding(top = safeInsets.top)
            insets
        }
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
        binding.buttonPick.setOnClickListener {
            parentFragmentManager.setFragmentResult(
                CHOOSE_YEAR_MODE_KEY,
                Bundle().apply {
                    putInt(CHOOSE_YEAR_FROM_NAME_KEY, viewModel.selectedYearFrom ?: Int.MIN_VALUE)
                    putInt(CHOOSE_YEAR_TO_NAME_KEY, viewModel.selectedYearTo ?: Int.MAX_VALUE)
                }
            )
            (requireActivity() as MainActivity).closeFragment()
        }
    }

    private fun checkButtonEnable() {
        binding.buttonPick.isEnabled =
            !(viewModel.selectedYearFrom == null && viewModel.selectedYearTo != null ||
                    viewModel.selectedYearFrom != null && viewModel.selectedYearTo == null)
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.state.collect { state ->
                    when (state) {
                        is SearchChooseDataState.Success -> {
                            adapterFrom.submitList(state.yearsFrom)
                            adapterTo.submitList(state.yearsTo)
                            binding.tvPeriodFrom.text = state.rangeFrom
                            binding.tvPeriodTo.text = state.rangeTo
                        }
                    }
                }
            }
        }
    }

    private fun setupBackButton() {
        binding.ivBack.setOnClickListener {
            (requireActivity() as MainActivity).closeFragment()
        }
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
}