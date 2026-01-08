package com.filimonov.afishamovies.presentation.ui.onboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import com.filimonov.afishamovies.R
import com.filimonov.afishamovies.databinding.FragmentOnBoardBinding
import com.filimonov.afishamovies.presentation.ui.MainActivity
import com.filimonov.afishamovies.presentation.ui.homepage.HomePageFragment


class OnBoardFragment : Fragment() {

    companion object {

        @JvmStatic
        fun newInstance() = OnBoardFragment()
    }

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
        setInsets()
        offBottomNav()
        setupViewPager()
        binding.tvSkip.setOnClickListener {
            launchHomePageFragment()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val safeInsets =
                insets.getInsets(WindowInsetsCompat.Type.systemBars() or WindowInsetsCompat.Type.displayCutout())
            v.updatePadding(
                top = safeInsets.top,
                left = safeInsets.left,
                right = safeInsets.right,
                bottom = safeInsets.bottom
            )
            insets
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
        OnBoardModel("Узнавай \nо премьерах", R.drawable.onboard_first),
        OnBoardModel("Создавай \nколлекции", R.drawable.onboard_second),
        OnBoardModel("Делись \nс друзьями", R.drawable.onboard_third),
    )

    private fun offBottomNav() {
        val bindingMain = (requireActivity() as MainActivity).binging
        bindingMain.bNav.visibility = View.INVISIBLE
    }

    private fun launchHomePageFragment() {
        requireActivity().supportFragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.slide_in_from_right,
                R.anim.slide_out_to_left,
                R.anim.slide_in_from_right,
                R.anim.slide_out_to_left
            )
            .replace(R.id.fragment_container, HomePageFragment.newInstance(), "HomePageFragment")
            .addToBackStack(null)
            .commit()
    }
}