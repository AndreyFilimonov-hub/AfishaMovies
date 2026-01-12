package com.filimonov.afishamovies.presentation.ui.filmpage

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.filimonov.afishamovies.AfishaMoviesApp
import com.filimonov.afishamovies.R
import com.filimonov.afishamovies.databinding.FragmentFilmPageBinding
import com.filimonov.afishamovies.domain.entities.FilmPageEntity
import com.filimonov.afishamovies.domain.entities.ImagePreviewEntity
import com.filimonov.afishamovies.presentation.ui.MainActivity
import com.filimonov.afishamovies.presentation.ui.filmpage.bottomsheetfragment.AddFilmPageToCollectionBottomSheetFragment
import com.filimonov.afishamovies.presentation.ui.filmpage.imagepreviewadapter.ImagePreviewAdapter
import com.filimonov.afishamovies.presentation.ui.filmpage.personadapter.ActorsItemDecoration
import com.filimonov.afishamovies.presentation.ui.filmpage.personadapter.PersonAdapter
import com.filimonov.afishamovies.presentation.ui.filmpage.personadapter.WorkersItemDecoration
import com.filimonov.afishamovies.presentation.ui.filmpage.similarmovieadapter.SimilarMovieAdapter
import com.filimonov.afishamovies.presentation.ui.gallery.GalleryFragment
import com.filimonov.afishamovies.presentation.ui.listpage.ListPageFragment
import com.filimonov.afishamovies.presentation.ui.listpage.ListPageMode
import com.filimonov.afishamovies.presentation.utils.HorizontalSpaceItemDecoration
import com.filimonov.afishamovies.presentation.utils.ViewAnimator
import com.filimonov.afishamovies.presentation.utils.ViewModelFactory
import com.filimonov.afishamovies.presentation.utils.setupDynamicStatusBar
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val MOVIE_ID = "movieId"
private const val MODE = "mode"

class FilmPageFragment : Fragment() {

    companion object {

        private const val UNDEFINED_ID = -1
        private const val UNDEFINED_MODE = ""
        private const val CREATE_BOTTOM_SHEET_TAG = "CREATE_BOTTOM_SHEET_TAG"

        @JvmStatic
        fun newInstance(movieId: Int, mode: String) =
            FilmPageFragment().apply {
                arguments = Bundle().apply {
                    putInt(MOVIE_ID, movieId)
                    putString(MODE, mode)
                }
            }
    }

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

    private var shortAnimationDuration: Long = 0

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[FilmPageViewModel::class.java]
    }

    private val viewAnimator = ViewAnimator()

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
            viewModel.addMediaBannerToInterestedCollection(it)
            val filmPageFragment = newInstance(it.id, FilmPageMode.DEFAULT.name)
            (requireActivity() as MainActivity).openFragment(filmPageFragment)
        }
    )

    private val imagePreviewAdapter = ImagePreviewAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseArgs()
        component.inject(this)
        shortAnimationDuration = resources.getInteger(android.R.integer.config_shortAnimTime).toLong()
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
        setupClickListeners()
        observeViewModel()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.state.collect { state ->
                    when (state) {
                        FilmPageState.Error -> {
                            with(viewAnimator) {
                                setupVisibilityVisible(binding.btnError, shortAnimationDuration)
                                setupVisibilityGone(binding.pbLoading, shortAnimationDuration)
                                setupVisibilityGone(binding.success, shortAnimationDuration)
                            }
                        }
                        FilmPageState.Loading -> {
                            with(viewAnimator) {
                                setupVisibilityVisible(binding.pbLoading, shortAnimationDuration)
                                setupVisibilityGone(binding.btnError, shortAnimationDuration)
                                setupVisibilityGone(binding.success, shortAnimationDuration)
                            }
                        }
                        is FilmPageState.Success -> {
                            setupFilmPageEntity(state.filmPage)
                            setupImagesToGallery(state.imagePreviews)

                            with(viewAnimator) {
                                setupVisibilityVisible(binding.success, shortAnimationDuration)
                                setupVisibilityGone(binding.pbLoading, shortAnimationDuration)
                                setupVisibilityGone(binding.btnError, shortAnimationDuration)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun setupImagesToGallery(list: List<ImagePreviewEntity>?) {
        list?.let {
            binding.rvGallery.adapter = imagePreviewAdapter
            binding.rvGallery.addItemDecoration(
                HorizontalSpaceItemDecoration(
                    requireContext().resources.getDimensionPixelSize(R.dimen.margin_start),
                    requireContext().resources.getDimensionPixelSize(R.dimen.space_between)
                )
            )
            imagePreviewAdapter.submitList(list)
        }
    }

    private fun setupFilmPageEntity(filmPage: FilmPageEntity) {
        with(filmPage) {
            binding.tvRatingName.text = this.ratingName
            binding.tvYearGenres.text = this.yearGenres
            binding.tvCountryTimeAge.text = this.countryMovieLengthAgeRating

            setupIconsView()

            if (this.shortDescription == null) {
                binding.tvShortDescription.visibility = View.GONE
            } else {
                binding.tvShortDescription.text = this.shortDescription
            }

            binding.tvFullDescription.text = this.description

            binding.tvAllPersonInFilm.text = viewModel.actorsCount()
            binding.tvAllWorkersInFilm.text = viewModel.workersCount()

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
                    val listPageFragment = ListPageFragment.newInstance(
                        this.id,
                        getString(R.string.similar_movie),
                        ListPageMode.SIMILAR_MOVIES
                    )
                    (requireActivity() as MainActivity).openFragment(listPageFragment)
                }
            } else {
                binding.tvSimilarMovie.visibility = View.GONE
                binding.tvAllSimilarMovie.visibility = View.GONE
                binding.rvSimilarMovie.visibility = View.GONE
            }

            setupDynamicStatusBar(
                (requireActivity() as MainActivity),
                binding.ivPoster,
                binding.success,
                posterUrl
            )

            binding.rvActors.adapter = actorsAdapter
            binding.rvActors.addItemDecoration(
                ActorsItemDecoration(
                    requireContext().resources.getDimensionPixelSize(R.dimen.margin_start),
                    requireContext().resources.getDimensionPixelSize(R.dimen.space_between),
                    requireContext().resources.getDimensionPixelSize(R.dimen.space_top_bottom_4dp)
                )
            )
            actorsAdapter.submitList(viewModel.getFirst20Actors())

            binding.rvWorker.adapter = workersAdapter
            binding.rvWorker.addItemDecoration(
                WorkersItemDecoration(
                    requireContext().resources.getDimensionPixelSize(R.dimen.margin_start),
                    requireContext().resources.getDimensionPixelSize(R.dimen.space_between),
                    requireContext().resources.getDimensionPixelSize(R.dimen.space_top_bottom_4dp)
                )
            )
            workersAdapter.submitList(viewModel.getFirst10Workers())

            binding.tvAllPersonInFilm.setOnClickListener {
                val listPageFragment = ListPageFragment.newInstance(
                    this.id,
                    getString(R.string.person_in_film),
                    ListPageMode.ACTOR
                )
                (requireActivity() as MainActivity).openFragment(listPageFragment)
            }

            binding.tvAllWorkersInFilm.setOnClickListener {
                val listPageFragment = ListPageFragment.newInstance(
                    this.id,
                    getString(R.string.worker_in_film),
                    ListPageMode.WORKER
                )
                (requireActivity() as MainActivity).openFragment(listPageFragment)
            }

            binding.tvAllGallery.setOnClickListener {
                val galleryFragment = GalleryFragment.newInstance(this.id)
                (requireActivity() as MainActivity).openFragment(galleryFragment)
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

    private fun setupClickListeners() {
        binding.ivLike.setOnClickListener {
            viewModel.toggleLike()
        }
        binding.ivWatched.setOnClickListener {
            viewModel.toggleWatched()
        }
        binding.ivWantToWatch.setOnClickListener {
            viewModel.toggleWantToWatch()
        }
        binding.ivMore.setOnClickListener {
            val bottomSheet = AddFilmPageToCollectionBottomSheetFragment.newInstance(movieId)
            bottomSheet.show(childFragmentManager, CREATE_BOTTOM_SHEET_TAG)
        }
        binding.btnError.setOnClickListener {
            viewModel.loadData()
        }
    }

    private fun setupIconsView() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.collectionState.collect { state ->
                    binding.ivLike.setImageResource(if (state.isLiked) R.drawable.like_active else R.drawable.like)
                    binding.ivWantToWatch.setImageResource(if (state.isWantToWatch) R.drawable.want_to_watch_active else R.drawable.want_to_watch)
                    binding.ivWatched.setImageResource(if (state.isWatched) R.drawable.watch else R.drawable.dont_watch)
                }
            }
        }
    }

    private fun setClickListenerOnBack() {
        binding.ivBack.setOnClickListener {
            (requireActivity() as MainActivity).closeFragment()
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
}