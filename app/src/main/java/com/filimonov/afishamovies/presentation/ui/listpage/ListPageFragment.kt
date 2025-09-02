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
import com.filimonov.afishamovies.presentation.model.MediaBannerUiModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.launch

private const val CATEGORY_ID = "category_id"
private const val TITLE = "title"

class ListPageFragment : Fragment() {

    private var _binding: FragmentListPageBinding? = null

    private val binding: FragmentListPageBinding
        get() = _binding ?: throw RuntimeException("FragmentListPageBinding == null")

    private var categoryId: Int = UNDEFINED_ID
    private var title: String = UNDEFINED_TITLE

    private lateinit var viewModel: ListPageViewModel

    private val mediaBannerGridAdapter =
        MediaBannerGridAdapter(
            onMediaBannerClick = {
                // TODO launch MediaPageFragment
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
        binding.rvContent.layoutManager = GridLayoutManager(this.context, 2)
        binding.rvContent.adapter = mediaBannerGridAdapter
        binding.rvContent.addItemDecoration(
            SpaceItemDecoration(
                resources.getDimensionPixelSize(R.dimen.margin_start60dp),
                resources.getDimensionPixelSize(R.dimen.margin_end60dp),
                resources.getDimensionPixelSize(R.dimen.margin_between16dp),
                resources.getDimensionPixelSize(R.dimen.margin_bottom16dp),
            )
        )
        binding.rvContent.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val layoutManager = recyclerView.layoutManager as GridLayoutManager
                val totalItemCount = layoutManager.itemCount
                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()

                if (!viewModel.isLoading.value && lastVisibleItem >= totalItemCount - 5) {
                    viewModel.nextPage()
                }
            }
        })
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.state.collect {
                    TransitionManager.beginDelayedTransition(binding.root)
                    when (it) {
                        ListPageState.Error -> {}
                        is ListPageState.Success -> {
                            mediaBannerGridAdapter.submitList(
                                it.mediaBanners
                                    .map { list -> MediaBannerUiModel.Banner(list) }
                            )
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
        binding.tvTitle.text = title
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
        val titleBundle = args.getString(TITLE) ?: ""
        if (titleBundle.isEmpty()) {
            throw RuntimeException("Param title is empty")
        }
        title = titleBundle
    }

    companion object {
        private const val UNDEFINED_ID = -1
        private const val UNDEFINED_TITLE = ""

        @JvmStatic
        fun newInstance(categoryId: Int, title: String) =
            ListPageFragment().apply {
                arguments = Bundle().apply {
                    putInt(CATEGORY_ID, categoryId)
                    putString(TITLE, title)
                }
            }
    }
}