package com.filimonov.afishamovies.presentation.ui.filmpage

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.transition.Slide
import androidx.transition.TransitionManager
import com.filimonov.afishamovies.AfishaMoviesApp
import com.filimonov.afishamovies.R
import com.filimonov.afishamovies.databinding.FragmentFilmPageBinding
import com.filimonov.afishamovies.domain.entities.FilmPageEntity
import com.filimonov.afishamovies.domain.entities.ImagePreviewEntity
import com.filimonov.afishamovies.presentation.ui.filmpage.imagepreviewadapter.ImagePreviewAdapter
import com.filimonov.afishamovies.presentation.ui.filmpage.personadapter.ActorsItemDecoration
import com.filimonov.afishamovies.presentation.ui.filmpage.personadapter.PersonAdapter
import com.filimonov.afishamovies.presentation.ui.filmpage.personadapter.WorkersItemDecoration
import com.filimonov.afishamovies.presentation.ui.filmpage.similarmovieadapter.SimilarMovieAdapter
import com.filimonov.afishamovies.presentation.ui.gallery.GalleryFragment
import com.filimonov.afishamovies.presentation.ui.listpage.ListPageFragment
import com.filimonov.afishamovies.presentation.ui.listpage.ListPageMode
import com.filimonov.afishamovies.presentation.utils.HorizontalSpaceItemDecoration
import com.filimonov.afishamovies.presentation.utils.ViewModelFactory
import com.filimonov.afishamovies.presentation.utils.loadImage
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val MOVIE_ID = "movieId"
private const val MODE = "mode"

class FilmPageFragment : Fragment() {

    private var movieId = UNDEFINED_ID
    private var mode = UNDEFINED_MODE

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

    private val actorsAdapter = PersonAdapter(
        onClick = {
            // TODO: launch ActorPageFragment
        }
    )

    private val workersAdapter = PersonAdapter(
        onClick = {
            // TODO: launch ActorPageFragment
        }
    )

    private val similarMovieAdapter = SimilarMovieAdapter(
        onClick = {
            requireActivity().supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, newInstance(it.id, FilmPageMode.DEFAULT.name))
                .addToBackStack(null)
                .commit()
        }
    )

    private val imagePreviewAdapter = ImagePreviewAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseArgs()
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
        setClickListenerOnBack()
        observeViewModel()
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
                            setupImagesToGallery(state.imagePreviews)
                        }
                    }
                }
            }
        }
    }

    private fun setupImagesToGallery(list: List<ImagePreviewEntity>) {
        binding.rvGallery.adapter = imagePreviewAdapter
        binding.rvGallery.addItemDecoration(
            HorizontalSpaceItemDecoration(
                requireContext().resources.getDimensionPixelSize(R.dimen.margin_start),
                requireContext().resources.getDimensionPixelSize(R.dimen.space_between)
            )
        )
        imagePreviewAdapter.submitList(list)
    }

    private fun setupFilmPageEntity(filmPage: FilmPageEntity) {
        with(filmPage) {
            binding.tvRatingName.text = this.ratingName
            binding.tvYearGenres.text = this.yearGenres
            binding.tvCountryTimeAge.text = this.countryMovieLengthAgeRating

            if (this.shortDescription == null) {
                binding.tvShortDescription.visibility = View.GONE
            } else {
                binding.tvShortDescription.text = this.shortDescription
            }

            binding.tvFullDescription.text = this.description

            binding.tvAllPersonInFilm.text = viewModel.actorsCount().toString()
            binding.tvAllWorkersInFilm.text = viewModel.workersCount().toString()

            if (this.similarMovies != null) {
                binding.tvAllSimilarMovie.text = this.similarMovies.size.toString()
                binding.rvSimilarMovie.adapter = similarMovieAdapter
                binding.rvSimilarMovie.addItemDecoration(
                    HorizontalSpaceItemDecoration(
                        requireContext().resources.getDimensionPixelSize(R.dimen.margin_start),
                        requireContext().resources.getDimensionPixelSize(R.dimen.space_between)
                    )
                )
                similarMovieAdapter.submitList(viewModel.getFirst10SimilarMovies())
                binding.tvAllSimilarMovie.setOnClickListener {
                    requireActivity().supportFragmentManager.beginTransaction()
                        .add(
                            R.id.fragment_container,
                            ListPageFragment.newInstance(
                                this.id,
                                R.string.similar_movie,
                                ListPageMode.SIMILAR_MOVIES
                            )
                        )
                        .addToBackStack(null)
                        .commit()
                }
            } else {
                binding.tvSimilarMovie.visibility = View.GONE
                binding.tvAllSimilarMovie.visibility = View.GONE
                binding.rvSimilarMovie.visibility = View.GONE
            }

            binding.ivPoster.loadImage(this.posterUrl)

            binding.rvActors.adapter = actorsAdapter
            binding.rvActors.addItemDecoration(
                ActorsItemDecoration(
                    requireContext().resources.getDimensionPixelSize(R.dimen.margin_start),
                    requireContext().resources.getDimensionPixelSize(R.dimen.space_between),
                    requireContext().resources.getDimensionPixelSize(R.dimen.space_top_bottom)
                )
            )
            actorsAdapter.submitList(viewModel.getFirst20Actors())

            binding.rvWorker.adapter = workersAdapter
            binding.rvWorker.addItemDecoration(
                WorkersItemDecoration(
                    requireContext().resources.getDimensionPixelSize(R.dimen.margin_start),
                    requireContext().resources.getDimensionPixelSize(R.dimen.space_between),
                    requireContext().resources.getDimensionPixelSize(R.dimen.space_top_bottom)
                )
            )
            workersAdapter.submitList(viewModel.getFirst10Workers())

            binding.tvAllPersonInFilm.setOnClickListener {
                requireActivity().supportFragmentManager.beginTransaction()
                    .add(
                        R.id.fragment_container,
                        ListPageFragment.newInstance(
                            this.id,
                            R.string.person_in_film,
                            ListPageMode.ACTOR
                        )
                    )
                    .addToBackStack(null)
                    .commit()
            }

            binding.tvAllWorkersInFilm.setOnClickListener {
                requireActivity().supportFragmentManager.beginTransaction()
                    .add(
                        R.id.fragment_container,
                        ListPageFragment.newInstance(
                            this.id,
                            R.string.worker_in_film,
                            ListPageMode.WORKER
                        )
                    )
                    .addToBackStack(null)
                    .commit()
            }

            binding.tvAllGallery.setOnClickListener {
                requireActivity().supportFragmentManager.beginTransaction()
                    .add(R.id.fragment_container, GalleryFragment.newInstance(this.id))
                    .addToBackStack(null)
                    .commit()
            }

            binding.ivRepost.setOnClickListener {
                val deepLink = getString(R.string.deeplink_film, this.shortDescription, this.id)

                val sendIntent = Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(Intent.EXTRA_TEXT, deepLink)
                }

                val shareIntent = Intent.createChooser(sendIntent, getString(R.string.share_via))
                startActivity(shareIntent)
            }
        }
    }

    private fun setClickListenerOnBack() {
        if (FilmPageMode.valueOf(mode) == FilmPageMode.DEFAULT) {
            binding.ivBack.setOnClickListener {
                requireActivity().supportFragmentManager.popBackStack()
            }
        } else {
            binding.ivBack.visibility = View.INVISIBLE
            requireActivity().onBackPressedDispatcher.addCallback(
                viewLifecycleOwner,
                object : OnBackPressedCallback(true) {
                    override fun handleOnBackPressed() {
                        if (parentFragmentManager.backStackEntryCount > 1) {
                            requireActivity().supportFragmentManager.popBackStack()
                        } else {
                            requireActivity().finish()
                        }
                    }
                })
        }
    }


    private fun parseArgs() {
        val args = requireArguments()
        if (!args.containsKey(MOVIE_ID)) {
            throw RuntimeException("param movie id is absent")
        }
        val idBundle = args.getInt(MOVIE_ID)
        if (idBundle < 0) {
            throw RuntimeException("wrong id")
        }
        movieId = idBundle
        if (!args.containsKey(MODE)) {
            throw RuntimeException("param mode is absent")
        }
        val modeBundle = args.getString(MODE) ?: ""
        if (modeBundle.isEmpty()) {
            throw RuntimeException("wrong mode")
        }
        mode = modeBundle
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
        private const val UNDEFINED_MODE = ""

        @JvmStatic
        fun newInstance(movieId: Int, mode: String) =
            FilmPageFragment().apply {
                arguments = Bundle().apply {
                    putInt(MOVIE_ID, movieId)
                    putString(MODE, mode)
                }
            }
    }
}