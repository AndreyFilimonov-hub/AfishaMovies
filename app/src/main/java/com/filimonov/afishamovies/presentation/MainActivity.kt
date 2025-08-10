package com.filimonov.afishamovies.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.filimonov.afishamovies.R
import com.filimonov.afishamovies.data.network.ApiFactory
import com.filimonov.afishamovies.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    val binging: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binging.root)
        launchOnBoardFragment()
    }

    private fun launchOnBoardFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, OnBoardFragment())
            .commit()
    }
}