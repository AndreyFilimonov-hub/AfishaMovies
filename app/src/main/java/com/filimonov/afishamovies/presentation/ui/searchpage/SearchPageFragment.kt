package com.filimonov.afishamovies.presentation.ui.searchpage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.filimonov.afishamovies.databinding.FragmentSearchPageBinding

class SearchPageFragment : Fragment() {

    private var _binding: FragmentSearchPageBinding? = null

    private val binding: FragmentSearchPageBinding
        get() = _binding ?: throw RuntimeException("FragmentSearchPageBinding == null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchPageBinding.inflate(inflater)
        return binding.root
    }

    companion object {

        @JvmStatic
        fun newInstance() = SearchPageFragment()
    }
}