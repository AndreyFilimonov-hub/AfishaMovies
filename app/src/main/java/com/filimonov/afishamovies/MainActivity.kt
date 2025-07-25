package com.filimonov.afishamovies

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.filimonov.afishamovies.databinding.ActivityMainBinding

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