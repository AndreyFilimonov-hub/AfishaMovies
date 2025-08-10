package com.filimonov.afishamovies.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.filimonov.afishamovies.R
import com.filimonov.afishamovies.databinding.FragmentHomePageBinding
import com.filimonov.afishamovies.presentation.adapters.HorizontalSpaceItemDecoration
import com.filimonov.afishamovies.presentation.adapters.MediaHorizontalAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomePageFragment : Fragment() {

    private var _binding: FragmentHomePageBinding? = null
    private val binding: FragmentHomePageBinding
        get() = _binding ?: throw RuntimeException("FragmentHomePageBinding == null")

    private val viewModel by lazy {
        ViewModelProvider(this)[HomePageViewModel::class.java]
    }

    private val adapterComedyRussian = MediaHorizontalAdapter()
    private val adapterPopular = MediaHorizontalAdapter()
    private val adapterActionUSA = MediaHorizontalAdapter()
    private val adapterTop250 = MediaHorizontalAdapter()
    private val adapterDramaFrance = MediaHorizontalAdapter()
    private val adapterSeries = MediaHorizontalAdapter()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomePageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onBottomNav()

        setPaddingNestedScroll()

        setupMediaRecyclerView(binding.rvComedyRussian, adapterComedyRussian)
        setupMediaRecyclerView(binding.rvPopular, adapterPopular)
        setupMediaRecyclerView(binding.rvActionUSA, adapterActionUSA)
        setupMediaRecyclerView(binding.rvTop250, adapterTop250)
        setupMediaRecyclerView(binding.rvDramaFrance, adapterDramaFrance)
        setupMediaRecyclerView(binding.rvSeries, adapterSeries)

        observeViewModel()
    }

    private fun setPaddingNestedScroll() {
        val bottomNavigationView = requireActivity().findViewById<BottomNavigationView>(R.id.bNav)
        val nestedScroll = binding.root

        bottomNavigationView.post {
            val bottomHeight = bottomNavigationView.height

            val layoutParams = nestedScroll.layoutParams as ViewGroup.MarginLayoutParams
            layoutParams.bottomMargin = bottomHeight
            nestedScroll.layoutParams = layoutParams
        }
    }

    private fun observeViewModel() {
        viewModel.comedyRussian.observe(viewLifecycleOwner) {
            adapterComedyRussian.submitList(it)
        }
        viewModel.popularMovies.observe(viewLifecycleOwner) {
            adapterPopular.submitList(it)
        }
        viewModel.actionUSA.observe(viewLifecycleOwner) {
            adapterActionUSA.submitList(it)
        }
        viewModel.top250.observe(viewLifecycleOwner) {
            adapterTop250.submitList(it)
        }
        viewModel.dramaFrance.observe(viewLifecycleOwner) {
            adapterDramaFrance.submitList(it)
        }
        viewModel.series.observe(viewLifecycleOwner) {
            adapterSeries.submitList(it)
        }
    }

    private fun setupMediaRecyclerView(rv: RecyclerView, adapter: MediaHorizontalAdapter) {
        rv.adapter = adapter
        rv.addItemDecoration(
            HorizontalSpaceItemDecoration(
                resources.getDimensionPixelSize(R.dimen.margin_start),
                resources.getDimensionPixelSize(R.dimen.space_between)
            )
        )
    }

    private fun onBottomNav() {
        val bindingMain = (requireActivity() as MainActivity).binging
        bindingMain.bNav.visibility = View.VISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            HomePageFragment()
    }
}