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
import com.filimonov.afishamovies.presentation.adapters.MoviesHorizontalAdapter
import com.filimonov.afishamovies.presentation.adapters.SeriesHorizontalAdapter

class HomePageFragment : Fragment() {

    private var _binding: FragmentHomePageBinding? = null
    private val binding: FragmentHomePageBinding
        get() = _binding ?: throw RuntimeException("FragmentHomePageBinding == null")

    private val viewModel by lazy {
        ViewModelProvider(this)[HomePageViewModel::class.java]
    }

    private val adapterComedyRussian = MoviesHorizontalAdapter()
    private val adapterPopular = MoviesHorizontalAdapter()
    private val adapterActionUSA = MoviesHorizontalAdapter()
    private val adapterTop250 = MoviesHorizontalAdapter()
    private val adapterDramaFrance = MoviesHorizontalAdapter()
    private val adapterSeries = SeriesHorizontalAdapter()


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

        setupMoviesRecyclerView(binding.rvComedyRussian, adapterComedyRussian)
        setupMoviesRecyclerView(binding.rvPopular, adapterPopular)
        setupMoviesRecyclerView(binding.rvActionUSA, adapterActionUSA)
        setupMoviesRecyclerView(binding.rvTop250, adapterTop250)
        setupMoviesRecyclerView(binding.rvDramaFrance, adapterDramaFrance)
        setupSeriesRecyclerView(binding.rvSeries, adapterSeries)

        observeViewModel()
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

    private fun setupMoviesRecyclerView(rv: RecyclerView, adapter: MoviesHorizontalAdapter) {
        rv.adapter = adapter
        rv.addItemDecoration(
            HorizontalSpaceItemDecoration(
                resources.getDimensionPixelSize(R.dimen.margin_start),
                resources.getDimensionPixelSize(R.dimen.space_between)
            )
        )
    }

    private fun setupSeriesRecyclerView(rv: RecyclerView, adapter: SeriesHorizontalAdapter) {
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