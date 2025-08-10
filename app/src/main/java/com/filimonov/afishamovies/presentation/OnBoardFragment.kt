package com.filimonov.afishamovies.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.filimonov.afishamovies.R
import com.filimonov.afishamovies.databinding.FragmentOnBoardBinding
import com.filimonov.afishamovies.presentation.adapters.ViewPagerAdapter


class OnBoardFragment : Fragment() {

    private var _binding: FragmentOnBoardBinding? = null
    private val binding: FragmentOnBoardBinding
        get() = _binding ?: throw RuntimeException("FragmentOnBoardBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOnBoardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        offBottomNav()
        setupViewPager()
        binding.tvSkip.setOnClickListener {
            launchHomePageFragment()
        }
    }

    private fun setupViewPager() {
        val viewPager = binding.viewPager
        val dotsIndicator = binding.dotsIndicator // TODO: remove library
        val adapter = ViewPagerAdapter(getOnBoardModels())
        viewPager.adapter = adapter
        dotsIndicator.attachTo(viewPager)
    }

    private fun getOnBoardModels() = listOf(
        OnBoardModel("Узнавай \nо премьерах" , R.drawable.onboard_first),
        OnBoardModel("Создавай \nколлекции", R.drawable.onboard_second),
        OnBoardModel("Делись \nс друзьями", R.drawable.onboard_third),
    )

    private fun offBottomNav() {
        val bindingMain = (requireActivity() as MainActivity).binging
        bindingMain.bNav.visibility = View.INVISIBLE
    }

    private fun launchHomePageFragment() {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, HomePageFragment.newInstance())
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}