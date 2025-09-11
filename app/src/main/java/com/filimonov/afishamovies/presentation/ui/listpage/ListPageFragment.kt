package com.filimonov.afishamovies.presentation.ui.listpage

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
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.Slide
import androidx.transition.TransitionManager
import com.filimonov.afishamovies.R
import com.filimonov.afishamovies.databinding.FragmentListPageBinding
import com.filimonov.afishamovies.presentation.ui.listpage.mediabannergridadapter.MediaBannerGridAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.launch

private const val CATEGORY_ID = "category_id"
private const val TITLE = "title"

class ListPageFragment : Fragment() {

    private var _binding: FragmentListPageBinding? = null

    private val binding: FragmentListPageBinding
        get() = _binding ?: throw RuntimeException("FragmentListPageBinding == null")

    private var categoryId: Int = UNDEFINED_ID
    private var titleId: Int = UNDEFINED_TITLE

    private lateinit var viewModel: ListPageViewModel

    private val mediaBannerGridAdapter =
        MediaBannerGridAdapter(
            onMediaBannerClick = {
                // TODO launch MediaPageFragment
            },
            onRetryButtonClick = {
                viewModel.nextPage()
            }
        )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseArgs()
        val viewModelFactory = ListPageViewModelProvider(categoryId)
        viewModel = ViewModelProvider(this, viewModelFactory)[ListPageViewModel::class.java]
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
        _binding = FragmentListPageBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setPaddingRootView()
        setToolbar()
        setupRecyclerView()
        observeViewModel()
        viewModel.nextPage()
    }

    private fun setupRecyclerView() {
        val layoutManager = GridLayoutManager(this.context, 2)
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return mediaBannerGridAdapter.getSpanPosition(position)
            }
        }
        binding.rvContent.layoutManager = layoutManager
        binding.rvContent.adapter = mediaBannerGridAdapter
        binding.rvContent.addItemDecoration(
            SpaceItemDecoration(
                mediaBannerGridAdapter,
                resources.getDimensionPixelSize(R.dimen.margin_between16dp),
                resources.getDimensionPixelSize(R.dimen.margin_bottom16dp)
            )
        )
        binding.rvContent.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val gridLayoutManager = recyclerView.layoutManager as GridLayoutManager
                val totalItemCount = gridLayoutManager.itemCount
                val lastVisibleItem = gridLayoutManager.findLastVisibleItemPosition()

                if (viewModel.state.value !is ListPageState.Loading && lastVisibleItem >= totalItemCount - 5) {
                    viewModel.nextPage()
                }
            }
        })
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                TransitionManager.beginDelayedTransition(binding.rvContent)
                viewModel.state.collect {
                    when (it) {
                        is ListPageState.Error -> {
                            mediaBannerGridAdapter.submitList(it.currentList)
                        }

                        is ListPageState.Success -> {
                            mediaBannerGridAdapter.submitList(it.mediaBanners)
                        }

                        is ListPageState.Loading -> {
                            mediaBannerGridAdapter.submitList(it.currentList)
                        }
                    }
                }
            }
        }
    }

    private fun setToolbar() {
        binding.ivBack.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
        binding.tvTitle.text = requireContext().resources.getText(titleId)
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

    private fun parseArgs() {
        val args = requireArguments()
        if (!args.containsKey(CATEGORY_ID)) {
            throw RuntimeException("Param category id is absent")
        }
        val categoryIdBundle = args.getInt(CATEGORY_ID)
        if (categoryIdBundle < 0) {
            throw RuntimeException("Category ID is wrong")
        }
        categoryId = categoryIdBundle
        if (!args.containsKey(TITLE)) {
            throw RuntimeException("Param title is absent")
        }
        val titleBundle = args.getInt(TITLE)
        if (titleBundle < 0) {
            throw RuntimeException("Param title is empty")
        }
        titleId = titleBundle
    }

    companion object {
        private const val UNDEFINED_ID = -1
        private const val UNDEFINED_TITLE = -1

        @JvmStatic
        fun newInstance(categoryId: Int, titleResId: Int) =
            ListPageFragment().apply {
                arguments = Bundle().apply {
                    putInt(CATEGORY_ID, categoryId)
                    putInt(TITLE, titleResId)
                }
            }
    }
}