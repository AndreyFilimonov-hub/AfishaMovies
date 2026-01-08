package com.filimonov.afishamovies.presentation.ui.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.TransitionManager
import com.filimonov.afishamovies.AfishaMoviesApp
import com.filimonov.afishamovies.R
import com.filimonov.afishamovies.databinding.FragmentGalleryBinding
import com.filimonov.afishamovies.domain.enums.TypeImage
import com.filimonov.afishamovies.presentation.ui.MainActivity
import com.filimonov.afishamovies.presentation.ui.gallery.imageadapter.ImageAdapter
import com.filimonov.afishamovies.presentation.ui.gallery.imageadapter.ImageSpaceDecoration
import com.filimonov.afishamovies.presentation.utils.ViewAnimator
import com.filimonov.afishamovies.presentation.utils.ViewModelFactory
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val MOVIE_ID = "movie_id"

class GalleryFragment : Fragment() {

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

    private var _binding: FragmentGalleryBinding? = null
    private val binding: FragmentGalleryBinding
        get() = _binding ?: throw RuntimeException("FragmentGalleryBinding == null")

    private var movieId: Int = UNDEFINED_ID

    private val component by lazy {
        (requireActivity().application as AfishaMoviesApp).component
            .galleryComponent()
            .create(movieId)
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[GalleryViewModel::class.java]
    }

    private lateinit var currentType: TypeImage

    private var isLastPage = false

    private val imageAdapter = ImageAdapter { reloadData() }

    private var shortAnimationDuration: Long = 0

    private val viewAnimator = ViewAnimator()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseInt()
        component.inject(this)

        shortAnimationDuration =
            resources.getInteger(android.R.integer.config_shortAnimTime).toLong()
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
        setInsets()
        setPaddingRootView()
        setupRecyclerView()
        setupClickListeners()
        setClickListenerOnBack()
        observeViewModel()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val safeInsets =
                insets.getInsets(WindowInsetsCompat.Type.systemBars() or WindowInsetsCompat.Type.displayCutout())
            v.updatePadding(top = safeInsets.top)
            insets
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                TransitionManager.beginDelayedTransition(binding.rvGallery)
                viewModel.state.collect { state ->
                    when (state) {
                        GalleryState.InitialLoading -> {
                            with(viewAnimator) {
                                setupVisibilityGone(binding.llNoInternet, shortAnimationDuration)
                                setupVisibilityVisible(binding.pbLoading, shortAnimationDuration)
                            }
                        }

                        GalleryState.InitialError -> {
                            with(viewAnimator) {
                                setupVisibilityGone(binding.pbLoading, shortAnimationDuration)
                                setupVisibilityGone(binding.rvGallery, shortAnimationDuration)
                                setupVisibilityVisible(binding.llNoInternet, shortAnimationDuration)
                            }
                        }

                        is GalleryState.Success -> {
                            with(viewAnimator) {
                                setupVisibilityGone(binding.llNoInternet, shortAnimationDuration)
                                setupVisibilityGone(binding.pbLoading, shortAnimationDuration)

                                isLastPage = state.isLastPage
                                currentType = state.selectedType
                                if (state.images.isNotEmpty()) {
                                    setupVisibilityGone(binding.tvEmpty, shortAnimationDuration)
                                    setupVisibilityVisible(binding.rvGallery, shortAnimationDuration)
                                    imageAdapter.submitList(state.images)
                                } else {
                                    setupVisibilityGone(binding.rvGallery, shortAnimationDuration)
                                    setupVisibilityVisible(binding.tvEmpty, shortAnimationDuration)
                                }
                            }
                        }

                        is GalleryState.Loading -> {
                            imageAdapter.submitList(state.images)
                        }

                        is GalleryState.Error -> {
                            imageAdapter.submitList(state.images)
                        }
                    }
                }
            }
        }
    }

    private fun setupClickListeners() {
        binding.chipFrame.setOnClickListener {
            viewModel.selectType(TypeImage.FRAME)
        }
        binding.chipBackstage.setOnClickListener {
            viewModel.selectType(TypeImage.BACKSTAGE)
        }
        binding.chipPoster.setOnClickListener {
            viewModel.selectType(TypeImage.POSTER)
        }
        binding.buttonReload.setOnClickListener {
            reloadData()
        }
    }

    private fun reloadData() {
        viewModel.loadData()
    }

    private fun setupRecyclerView() {
        val layoutManager = GridLayoutManager(this.context, 2)
        layoutManager.spanSizeLookup = object : SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return imageAdapter.getSpanPosition(position)
            }
        }

        binding.rvGallery.layoutManager = layoutManager

        binding.rvGallery.adapter = imageAdapter
        binding.rvGallery.addItemDecoration(
            ImageSpaceDecoration(
                imageAdapter,
                requireContext().resources.getDimensionPixelSize(R.dimen.margin_start),
                requireContext().resources.getDimensionPixelSize(R.dimen.margin_end),
                requireContext().resources.getDimensionPixelSize(R.dimen.space_between),
                requireContext().resources.getDimensionPixelSize(R.dimen.space_top_bottom_8dp)
            )
        )

        binding.rvGallery.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy <= 0) return

                val gridLayoutManager = recyclerView.layoutManager as GridLayoutManager
                val totalItems = gridLayoutManager.itemCount
                val lastVisibleItem = gridLayoutManager.findLastVisibleItemPosition()

                val state = viewModel.state.value

                if (!isLastPage &&
                    state !is GalleryState.Loading &&
                    lastVisibleItem >= totalItems - 5
                ) {
                    viewModel.loadData()
                }
            }
        })

        binding.rvGallery.itemAnimator = null
    }

    private fun setClickListenerOnBack() {
        binding.ivBack.setOnClickListener {
            (requireActivity() as MainActivity).closeFragment()
        }
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
}