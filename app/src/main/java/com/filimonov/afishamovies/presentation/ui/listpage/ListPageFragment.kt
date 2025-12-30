package com.filimonov.afishamovies.presentation.ui.listpage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.TransitionManager
import com.filimonov.afishamovies.AfishaMoviesApp
import com.filimonov.afishamovies.R
import com.filimonov.afishamovies.databinding.FragmentListPageBinding
import com.filimonov.afishamovies.presentation.ui.MainActivity
import com.filimonov.afishamovies.presentation.ui.filmpage.FilmPageFragment
import com.filimonov.afishamovies.presentation.ui.filmpage.FilmPageMode
import com.filimonov.afishamovies.presentation.ui.listpage.mediabannergridadapter.MediaBannerGridAdapter
import com.filimonov.afishamovies.presentation.utils.ViewModelFactory
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val CATEGORY_OR_MOVIE_ID = "category_or_movie_id"
private const val TITLE = "title"
private const val MODE = "mode"

class ListPageFragment : Fragment() {

    companion object {
        private const val UNDEFINED_ID = -1
        private const val UNDEFINED_TITLE = ""

        @JvmStatic
        fun newInstance(id: Int, title: String, mode: ListPageMode) =
            ListPageFragment().apply {
                arguments = Bundle().apply {
                    putInt(CATEGORY_OR_MOVIE_ID, id)
                    putString(TITLE, title)
                    putString(MODE, mode.name)
                }
            }
    }

    private var _binding: FragmentListPageBinding? = null
    private val binding: FragmentListPageBinding
        get() = _binding ?: throw RuntimeException("FragmentListPageBinding == null")

    private var id: Int = UNDEFINED_ID
    private var title: String = UNDEFINED_TITLE
    private lateinit var mode: ListPageMode

    private val component by lazy {
        (requireActivity().application as AfishaMoviesApp).component
            .listPageComponent()
            .create(id, mode)
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[ListPageViewModel::class.java]
    }

    private val mediaBannerGridAdapter by lazy {
        MediaBannerGridAdapter(
            onMediaBannerClick = {
                val filmPageFragment = FilmPageFragment.newInstance(it.id, FilmPageMode.DEFAULT.name)
                (requireActivity() as MainActivity).openFragment(filmPageFragment)
            },
            onPersonBannerClick = {
                // TODO: launch ActorPageFragment
            },
            onRetryButtonClick = {
                viewModel.nextPage()
            }
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseArgs()
        component.inject(this)
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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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
                resources.getDimensionPixelSize(R.dimen.margin_between_16dp),
                resources.getDimensionPixelSize(R.dimen.margin_bottom_16dp)
            )
        )
        binding.rvContent.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val gridLayoutManager = recyclerView.layoutManager as GridLayoutManager
                val totalItemCount = gridLayoutManager.itemCount
                val lastVisibleItem = gridLayoutManager.findLastVisibleItemPosition()

                if (mode == ListPageMode.MEDIA && viewModel.state.value !is ListPageState.Loading && lastVisibleItem >= totalItemCount - 5) {
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

                        ListPageState.Initial -> {}
                    }
                }
            }
        }
    }

    private fun setToolbar() {
        binding.ivBack.setOnClickListener {
            (requireActivity() as MainActivity).closeFragment()
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
        if (!args.containsKey(CATEGORY_OR_MOVIE_ID)) {
            throw RuntimeException("Param category id is absent")
        }
        val idBundle = args.getInt(CATEGORY_OR_MOVIE_ID)
        if (idBundle < 0) {
            throw RuntimeException("Category ID is wrong")
        }
        id = idBundle
        if (!args.containsKey(TITLE)) {
            throw RuntimeException("Param title is absent")
        }
        val titleBundle = args.getString(TITLE) ?: ""
        if (titleBundle.isEmpty()) {
            throw RuntimeException("Param title is empty")
        }
        title = titleBundle
        if (!args.containsKey(MODE)) {
            throw RuntimeException("Param mode is absent")
        }
        val modeBundle = args.getString(MODE) ?: ""
        if (modeBundle.isEmpty()) {
            throw RuntimeException("Param mode is empty")
        }
        mode = ListPageMode.valueOf(modeBundle)
    }
}