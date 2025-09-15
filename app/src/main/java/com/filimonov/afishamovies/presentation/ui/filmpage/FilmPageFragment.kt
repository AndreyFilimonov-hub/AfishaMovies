package com.filimonov.afishamovies.presentation.ui.filmpage

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import androidx.fragment.app.Fragment
import androidx.transition.Slide
import com.filimonov.afishamovies.R
import com.filimonov.afishamovies.databinding.FragmentFilmPageBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

private const val MOVIE_ID = "movieId"

class FilmPageFragment : Fragment() {
    private var movieId = UNDEFINED_ID

    private var _binding: FragmentFilmPageBinding? = null

    private val binding: FragmentFilmPageBinding
        get() = _binding ?: throw RuntimeException("FragmentFilmPageBinding == null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseInt()

        enterTransition = Slide(Gravity.END).apply {
            duration = 500L
            interpolator = AccelerateInterpolator()
            propagation = null
        }
        exitTransition = Slide(Gravity.START).apply {
            duration = 500L
            interpolator = AccelerateInterpolator()
            propagation = null
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFilmPageBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setPaddingRootView()
        Log.d("AAA", "id $movieId")
    }

    private fun parseInt() {
        val args = requireArguments()
        if (!args.containsKey(MOVIE_ID)) {
            throw RuntimeException("param movie id is absent")
        }
        val idBundle = args.getInt(MOVIE_ID)
        if (idBundle < 0) {
            throw RuntimeException("wrong id")
        }
        movieId = idBundle
    }

    private fun setPaddingRootView() {
        val bottomNavigationView = requireActivity().findViewById<BottomNavigationView>(R.id.bNav)
        val rootView = binding.root

        bottomNavigationView.post {
            val bottomHeight = bottomNavigationView.height

            val layoutParams = rootView.layoutParams as ViewGroup.MarginLayoutParams
            layoutParams.bottomMargin = bottomHeight
            rootView.layoutParams = layoutParams
        }
    }

    companion object {

        private const val UNDEFINED_ID = -1

        @JvmStatic
        fun newInstance(movieId: Int) =
            FilmPageFragment().apply {
                arguments = Bundle().apply {
                    putInt(MOVIE_ID, movieId)
                }
            }
    }
}