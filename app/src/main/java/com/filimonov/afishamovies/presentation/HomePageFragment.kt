package com.filimonov.afishamovies.presentation

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.filimonov.afishamovies.R
import com.filimonov.afishamovies.data.mapper.toEntity
import com.filimonov.afishamovies.data.network.ApiFactory
import com.filimonov.afishamovies.databinding.FragmentHomePageBinding
import com.filimonov.afishamovies.presentation.adapters.HorizontalSpaceItemDecoration
import com.filimonov.afishamovies.presentation.adapters.MoviesHorizontalAdapter
import kotlinx.coroutines.launch

class HomePageFragment : Fragment() {

    private var _binding: FragmentHomePageBinding? = null
    private val binding: FragmentHomePageBinding
        get() = _binding ?: throw RuntimeException("FragmentHomePageBinding == null")


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomePageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapterPremier = MoviesHorizontalAdapter()
        val adapterPopular = MoviesHorizontalAdapter()
        binding.rvComedyRussian.adapter = adapterPremier
        binding.rvComedyRussian.addItemDecoration(
            HorizontalSpaceItemDecoration(
                resources.getDimensionPixelSize(R.dimen.margin_start),
                resources.getDimensionPixelSize(R.dimen.space_between)
            )
        )
        binding.rvPopular.adapter = adapterPopular
        binding.rvPopular.addItemDecoration(
            HorizontalSpaceItemDecoration(
                resources.getDimensionPixelSize(R.dimen.margin_start),
                resources.getDimensionPixelSize(R.dimen.space_between)
            )
        )
        onBottomNav()
        lifecycleScope.launch {
            try {
                val premier = ApiFactory.apiService.getComedyRussiaMovieList()
                val popular = ApiFactory.apiService.getPopularMovieList()
                Log.d("AAA", premier.toString())
                val a = premier.movies.map {
                    it.toEntity()
                }
                val b = popular.movies.map {
                    it.toEntity()
                }
                adapterPopular.submitList(b)
                adapterPremier.submitList(a)
            } catch (e: Exception) {
                Log.d("AAA", e.toString())
            }
        }
    }

    private fun onBottomNav() {
        val bindingMain = (requireActivity() as MainActivity).binging
        bindingMain.bNav.visibility = View.VISIBLE
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            HomePageFragment()
    }
}