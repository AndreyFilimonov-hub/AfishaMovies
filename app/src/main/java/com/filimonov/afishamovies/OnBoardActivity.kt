package com.filimonov.afishamovies

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.filimonov.afishamovies.databinding.ActivityOnBoardBinding

class OnBoardActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityOnBoardBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        setupViewPager()

        binding.tvSkip.setOnClickListener {
            // TODO: start MainActivity
        }
    }

    private fun setupViewPager() {
        val viewPager = binding.viewPager
        val dotsIndicator = binding.dotsIndicator // TODO: remove library
        val adapter = ViewPagerAdapter(getOnBoardModels())
        viewPager.adapter = adapter
        dotsIndicator.attachTo(viewPager)
    }

    private fun getOnBoardModels() = listOf<OnBoardModel>(
        OnBoardModel("Узнавай \nо премьерах" ,R.drawable.onboard_first),
        OnBoardModel("Создавай \nколлекции", R.drawable.onboard_second),
        OnBoardModel("Делись \nс друзьями", R.drawable.onboard_third),
    )
}