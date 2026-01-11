package com.filimonov.afishamovies.presentation.ui.profilepage

import android.animation.LayoutTransition
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
import androidx.recyclerview.widget.LinearLayoutManager
import com.filimonov.afishamovies.AfishaMoviesApp
import com.filimonov.afishamovies.R
import com.filimonov.afishamovies.databinding.FragmentProfilePageBinding
import com.filimonov.afishamovies.domain.enums.DefaultCollection
import com.filimonov.afishamovies.presentation.ui.MainActivity
import com.filimonov.afishamovies.presentation.ui.filmpage.FilmPageFragment
import com.filimonov.afishamovies.presentation.ui.filmpage.FilmPageMode
import com.filimonov.afishamovies.presentation.ui.listpage.ListPageFragment
import com.filimonov.afishamovies.presentation.ui.listpage.ListPageMode
import com.filimonov.afishamovies.presentation.ui.profilepage.collectionadapter.CollectionAdapter
import com.filimonov.afishamovies.presentation.ui.profilepage.collectionadapter.CollectionItemDecoration
import com.filimonov.afishamovies.presentation.ui.profilepage.dialogfragment.CreateCollectionDialog
import com.filimonov.afishamovies.presentation.ui.profilepage.mediabanneradapter.MediaBannerAdapter
import com.filimonov.afishamovies.presentation.utils.HorizontalSpaceItemDecoration
import com.filimonov.afishamovies.presentation.utils.ViewModelFactory
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProfilePageFragment : Fragment() {

    companion object {

        private const val CREATE_COLLECTION_TAG = "create_collection_dialog"

        @JvmStatic
        fun newInstance() = ProfilePageFragment()
    }

    private var _binding: FragmentProfilePageBinding? = null

    private val binding: FragmentProfilePageBinding
        get() = _binding ?: throw RuntimeException("FragmentProfilePageBinding == null")

    private val component by lazy {
        (requireActivity().application as AfishaMoviesApp).component
            .profilePageComponent()
            .create()
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[ProfilePageViewModel::class]
    }

    private val watchedMediaBannerAdapter = MediaBannerAdapter(
        onMediaBannerClick = {
            viewModel.addMediaBannerToInterestedCollection(it)
            val filmPageFragment = FilmPageFragment.newInstance(it.id, FilmPageMode.DEFAULT.name)
            (requireActivity() as MainActivity).openFragment(filmPageFragment)
        },
        onClearHistoryClick = {
            viewModel.clearCollection(DefaultCollection.WATCHED)
        }
    )

    private val interestedMediaBannerAdapter = MediaBannerAdapter(
        onMediaBannerClick = {
            viewModel.addMediaBannerToInterestedCollection(it)
            val filmPageFragment = FilmPageFragment.newInstance(it.id, FilmPageMode.DEFAULT.name)
            (requireActivity() as MainActivity).openFragment(filmPageFragment)
        },
        onClearHistoryClick = {
            viewModel.clearCollection(DefaultCollection.INTERESTED)
        }
    )

    private val collectionAdapter = CollectionAdapter(
        onCollectionClickListener = { collection ->
            val listPageFragment = ListPageFragment.newInstance(
                collection.id,
                collection.name,
                ListPageMode.COLLECTION
            )
            (requireActivity() as MainActivity).openFragment(listPageFragment)
        },
        onDeleteCollectionClick = { id ->
            viewModel.deleteCollection(id)
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        component.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfilePageBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val safeInsets = insets.getInsets(WindowInsetsCompat.Type.statusBars() or WindowInsetsCompat.Type.displayCutout())
            v.updatePadding(top = safeInsets.top)
            insets
        }
        setPaddingRootView()
        setLayoutTransition()
        setupRecyclerView()
        setupClickListeners()
        observeViewModel()
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.state.collect { state ->
                    when (state) {
                        ProfilePageState.Error -> {
                        }

                        ProfilePageState.Loading -> {
                        }

                        is ProfilePageState.Success -> {
                            processingSuccessState(state)
                        }
                    }
                }
            }
        }
    }

    private fun processingSuccessState(state: ProfilePageState.Success) {
        if (state.watchedList.size == 1) {
            binding.rvWatched.visibility = View.GONE
            binding.tvEmptyWatched.visibility = View.VISIBLE
        } else {
            binding.rvWatched.visibility = View.VISIBLE
            binding.tvEmptyWatched.visibility = View.GONE
        }

        if (state.interestedList.size == 1) {
            binding.rvWasInteresting.visibility = View.GONE
            binding.tvEmptyInterested.visibility = View.VISIBLE
        } else {
            binding.rvWasInteresting.visibility = View.VISIBLE
            binding.tvEmptyInterested.visibility = View.GONE
        }

        watchedMediaBannerAdapter.submitList(state.watchedList)
        interestedMediaBannerAdapter.submitList(state.interestedList) {
            with(binding.rvWasInteresting) {
                post {
                    val layoutManager = this.layoutManager as LinearLayoutManager
                    layoutManager.scrollToPositionWithOffset(0, 0)
                }
            }
        }
        collectionAdapter.submitList(state.collectionList)

        binding.tvAllWatched.apply {
            val count = state.watchedListSize
            if (count.isEmpty()) {
                this.visibility = View.GONE
            } else {
                this.text = count
                this.visibility = View.VISIBLE
            }
        }
        binding.tvAllWasInteresting.apply {
            val count = state.interestedListSize
            if (count.isEmpty()) {
                this.visibility = View.GONE
            } else {
                this.text = count
                this.visibility = View.VISIBLE
            }
        }
    }

    private fun setupClickListeners() {
        binding.tvAddCollection.setOnClickListener {
            val dialog = CreateCollectionDialog { name ->
                viewModel.createCollection(name, DefaultCollection.USER)
            }
            dialog.show(parentFragmentManager, CREATE_COLLECTION_TAG)
        }
        binding.tvAllWatched.setOnClickListener {
            val listPageFragment = ListPageFragment.newInstance(
                viewModel.getWatchedCollectionId(),
                getString(R.string.watched),
                ListPageMode.COLLECTION
            )
            (requireActivity() as MainActivity).openFragment(listPageFragment)
        }
        binding.tvAllWasInteresting.setOnClickListener {
            val listPageFragment = ListPageFragment.newInstance(
                viewModel.getInterestedCollectionId(),
                getString(R.string.you_have_interested),
                ListPageMode.COLLECTION
            )
            (requireActivity() as MainActivity).openFragment(listPageFragment)
        }
    }

    private fun setupRecyclerView() {
        with(binding.rvWatched) {
            adapter = watchedMediaBannerAdapter
            addItemDecoration(
                HorizontalSpaceItemDecoration(
                    requireContext().resources.getDimensionPixelSize(R.dimen.margin_start),
                    requireContext().resources.getDimensionPixelSize(R.dimen.space_between)
                )
            )
        }
        with(binding.rvWasInteresting) {
            adapter = interestedMediaBannerAdapter
            addItemDecoration(
                HorizontalSpaceItemDecoration(
                    requireContext().resources.getDimensionPixelSize(R.dimen.margin_start),
                    requireContext().resources.getDimensionPixelSize(R.dimen.space_between)
                )
            )
        }
        with(binding.rvCollections) {
            adapter = collectionAdapter
            addItemDecoration(CollectionItemDecoration(requireContext()))
        }
    }

    private fun setLayoutTransition() {
        val transition = LayoutTransition()
        transition.enableTransitionType(LayoutTransition.CHANGING)
        binding.constraint.layoutTransition = transition
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}