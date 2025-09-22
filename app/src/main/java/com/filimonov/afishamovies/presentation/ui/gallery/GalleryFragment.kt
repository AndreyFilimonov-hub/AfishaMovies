package com.filimonov.afishamovies.presentation.ui.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.filimonov.afishamovies.databinding.FragmentGalleryBinding

private const val MOVIE_ID = "movie_id"

class GalleryFragment : Fragment() {

    private var _binding: FragmentGalleryBinding? = null

    private val binding: FragmentGalleryBinding
        get() = _binding ?: throw RuntimeException("FragmentGalleryBinding == null")

    private var movieId: Int = UNDEFINED_ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseInt()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGalleryBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    private fun parseInt() {
        val args = requireArguments()
        if (!args.containsKey(MOVIE_ID)) {
            throw RuntimeException("Param movieId is absent")
        }
        val movieIdBundle = args.getInt(MOVIE_ID)
        if (movieIdBundle < 0) {
            throw RuntimeException("Param movieId is wrong")
        }
        movieId = movieIdBundle
    }

    companion object {

        private const val UNDEFINED_ID = -1

        @JvmStatic
        fun newInstance(movieId: Int) =
            GalleryFragment().apply {
                arguments = Bundle().apply {
                    putInt(MOVIE_ID, movieId)
                }
            }
    }
}