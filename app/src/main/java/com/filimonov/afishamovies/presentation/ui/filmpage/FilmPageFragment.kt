package com.filimonov.afishamovies.presentation.ui.filmpage

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.transition.Slide
import androidx.transition.TransitionManager
import com.bumptech.glide.Glide
import com.filimonov.afishamovies.AfishaMoviesApp
import com.filimonov.afishamovies.R
import com.filimonov.afishamovies.databinding.FragmentFilmPageBinding
import com.filimonov.afishamovies.domain.entities.FilmPageEntity
import com.filimonov.afishamovies.presentation.utils.ViewModelFactory
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val MOVIE_ID = "movieId"

class FilmPageFragment : Fragment() {
    private var movieId = UNDEFINED_ID

    private var _binding: FragmentFilmPageBinding? = null

    private val binding: FragmentFilmPageBinding
        get() = _binding ?: throw RuntimeException("FragmentFilmPageBinding == null")

    private val component by lazy {
        (requireActivity().application as AfishaMoviesApp).component
            .filmPageComponent()
            .create(movieId)
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[FilmPageViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseInt()
        component.inject(this)
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
        observeViewModel()
        Log.d("AAA", "id $movieId")
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                TransitionManager.beginDelayedTransition(binding.root)
                viewModel.state.collect { state ->
                    when (state) {
                        FilmPageState.Error -> {}
                        FilmPageState.Loading -> {}
                        is FilmPageState.Success -> {
                            setupFilmPageEntity(state.filmPage)
                        }
                    }
                }
            }
        }
    }

    private fun setupFilmPageEntity(filmPage: FilmPageEntity) {
        with(filmPage) {
            if (this.shortDescription == null) {
                binding.tvShortDescription.visibility = View.GONE
            }
            binding.tvShortDescription.text = this.shortDescription
            binding.tvFullDescription.text = this.description
            binding.tvAllPersonInFilm.text = viewModel.actorsCount().toString()
            binding.tvAllWorkersInFilm.text = viewModel.workersCount().toString()
            if (this.similarMovies != null) {
                binding.tvAllSimilarMovie.text = this.similarMovies.size.toString()
            } else {
                binding.tvSimilarMovie.visibility = View.GONE
                binding.tvAllSimilarMovie.visibility = View.GONE
            }
            Glide.with(binding.ivPoster)
                .load(this.posterUrl)
                .into(binding.ivPoster)
        }
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