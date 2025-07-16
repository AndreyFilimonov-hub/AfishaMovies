package com.filimonov.afishamovies

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.filimonov.afishamovies.databinding.ActivityOnBoardBinding

class OnBoardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnBoardBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnBoardBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        setupViewPager()

        binding.tvSkip.setOnClickListener {
            // start HomeActivity
        }
    }

    private fun setupViewPager() {
        val viewPager = binding.viewPager
        val dotsIndicator = binding.dotsIndicator
        val adapter = ViewPagerAdapter()
        viewPager.adapter = adapter
        dotsIndicator.attachTo(viewPager)
    }
}