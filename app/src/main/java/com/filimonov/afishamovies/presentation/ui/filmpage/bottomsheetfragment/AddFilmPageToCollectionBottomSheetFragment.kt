package com.filimonov.afishamovies.presentation.ui.filmpage.bottomsheetfragment

import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.filimonov.afishamovies.AfishaMoviesApp
import com.filimonov.afishamovies.databinding.BottomSheetLayoutBinding
import com.filimonov.afishamovies.presentation.ui.filmpage.bottomsheetfragment.collectionadapter.CollectionWithMovieAdapter
import com.filimonov.afishamovies.presentation.ui.profilepage.dialogfragment.CreateCollectionDialog
import com.filimonov.afishamovies.presentation.utils.ViewModelFactory
import com.filimonov.afishamovies.presentation.utils.loadImageBanner
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.launch
import javax.inject.Inject

class AddFilmPageToCollectionBottomSheetFragment() : BottomSheetDialogFragment() {

    companion object {

        private const val UNDEFINED_ID = -1
        private const val MOVIE_ID_KEY = "MOVIE_ID_KEY"

        private const val CREATE_COLLECTION_TAG = "CREATE_COLLECTION_TAG"

        @JvmStatic
        fun newInstance(movieId: Int) = AddFilmPageToCollectionBottomSheetFragment().apply {
            arguments = Bundle().apply {
                putInt(MOVIE_ID_KEY, movieId)
            }
        }
    }

    private var _binding: BottomSheetLayoutBinding? = null
    private val binding: BottomSheetLayoutBinding
        get() = _binding ?: throw RuntimeException("BottomSheetLayoutBinding == null")

    private var movieId: Int = UNDEFINED_ID

    private val component by lazy {
        (requireActivity().application as AfishaMoviesApp).component
            .filmPageComponent()
            .create(movieId)
            .createAddFilmPageToCollectionComponent()
            .create()
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[AddFilmPageToCollectionViewModel::class]
    }

    private val collectionWithMovieAdapter = CollectionWithMovieAdapter { collection, isChecked ->
        if (isChecked) {
            viewModel.saveToCollections(collection)
        } else {
            viewModel.deleteFromCollection(collection)
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        dialog.setOnShowListener {
            val bottomSheet =
                dialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            bottomSheet?.setBackgroundColor(Color.WHITE)
        }
        return dialog
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        movieId = arguments?.getInt(MOVIE_ID_KEY) ?: UNDEFINED_ID
        component.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomSheetLayoutBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvCollections.adapter = collectionWithMovieAdapter
        observeViewModel()
        setupClickListeners()
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.state.collect { state ->
                    when (state) {
                        AddFilmPageToCollectionState.Error -> Unit
                        AddFilmPageToCollectionState.Initial -> Unit
                        is AddFilmPageToCollectionState.Success -> {
                            with(binding) {
                                ivPoster.loadImageBanner(state.mediaBannerEntity.posterUrl)
                                tvRating.text = state.mediaBannerEntity.rating
                                tvName.text = state.mediaBannerEntity.name
                                tvGenre.text = state.mediaBannerEntity.genreMain
                            }
                            collectionWithMovieAdapter.submitList(state.collections)
                        }
                    }
                }
            }
        }
    }

    private fun setupClickListeners() {
        binding.ivClose.setOnClickListener { dismiss() }
        binding.tvAddCollection.setOnClickListener {
            val createCollectionDialog = CreateCollectionDialog { name ->
                viewModel.createCollection(name)
            }
            createCollectionDialog.show(parentFragmentManager, CREATE_COLLECTION_TAG)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}