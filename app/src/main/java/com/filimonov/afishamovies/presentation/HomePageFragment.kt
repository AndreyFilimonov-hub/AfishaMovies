package com.filimonov.afishamovies.presentation

import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import com.filimonov.afishamovies.R
import com.filimonov.afishamovies.data.mapper.toEntity
import com.filimonov.afishamovies.data.network.ApiFactory
import com.filimonov.afishamovies.databinding.FragmentHomePageBinding
import com.filimonov.afishamovies.databinding.FragmentOnBoardBinding
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
        val adapter = MoviesHorizontalAdapter()
        binding.rvPremier.adapter = adapter
        binding.rvPremier.addItemDecoration(
            HorizontalSpaceItemDecoration(
                resources.getDimensionPixelSize(R.dimen.margin_start),
                resources.getDimensionPixelSize(R.dimen.space_between)
            )
        )
        onBottomNav()
        lifecycleScope.launch {
            try {
                val list = ApiFactory.apiService.getDramaFranceMovieList()
                Log.d("AAA", list.toString())
                val a = list.movies.map {
                    it.toEntity()
                }
                adapter.submitList(a)
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