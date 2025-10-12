package com.filimonov.afishamovies.presentation.ui.gallery

import android.os.Bundle
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
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.Slide
import androidx.transition.TransitionManager
import com.filimonov.afishamovies.AfishaMoviesApp
import com.filimonov.afishamovies.R
import com.filimonov.afishamovies.databinding.FragmentGalleryBinding
import com.filimonov.afishamovies.domain.enums.TypeImage
import com.filimonov.afishamovies.presentation.ui.gallery.imageadapter.ImageAdapter
import com.filimonov.afishamovies.presentation.ui.gallery.imageadapter.ImageSpaceDecoration
import com.filimonov.afishamovies.presentation.utils.ViewModelFactory
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val MOVIE_ID = "movie_id"

class GalleryFragment : Fragment() {

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

    private val imageAdapter = ImageAdapter {
        reloadData()
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
        _binding = FragmentGalleryBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setPaddingRootView()
        setupRecyclerView()
        setupClickListeners()
        setClickListenerOnBack()
        observeViewModel()
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                TransitionManager.beginDelayedTransition(binding.rvGallery)
                viewModel.state.collect { state ->
                    when (state) {
                        GalleryState.InitialLoading -> {
                            binding.pbLoading.visibility = View.VISIBLE
                            binding.llNoInternet.visibility = View.INVISIBLE
                        }

                        GalleryState.InitialError -> {
                            binding.pbLoading.visibility = View.INVISIBLE
                            binding.llNoInternet.visibility = View.VISIBLE
                            binding.rvGallery.visibility = View.INVISIBLE
                        }

                        is GalleryState.Success -> {
                            binding.llNoInternet.visibility = View.INVISIBLE
                            binding.pbLoading.visibility = View.INVISIBLE

                            isLastPage = state.isLastPage
                            currentType = state.selectedType
                            if (state.images.isNotEmpty()) {
                                binding.tvEmpty.visibility = View.INVISIBLE
                                binding.rvGallery.visibility = View.VISIBLE
                                imageAdapter.submitList(state.images)
                            } else {
                                binding.rvGallery.visibility = View.INVISIBLE
                                binding.tvEmpty.visibility = View.VISIBLE
                            }
                        }

                        is GalleryState.Loading -> {
                            imageAdapter.submitList(state.images)
                        }

                        is GalleryState.Error -> {
                            imageAdapter.submitList(state.images)
                            binding.rvGallery.visibility = View.VISIBLE
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
                requireContext().resources.getDimensionPixelSize(R.dimen.space_top_bottom8dp)
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
            requireActivity().supportFragmentManager.popBackStack()
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