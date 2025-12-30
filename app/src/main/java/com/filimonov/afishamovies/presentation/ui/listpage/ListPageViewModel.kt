package com.filimonov.afishamovies.presentation.ui.listpage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.filimonov.afishamovies.data.mapper.toListPageMediaActorBanners
import com.filimonov.afishamovies.data.mapper.toListPageMediaBannerList
import com.filimonov.afishamovies.data.mapper.toListPageMediaWorkerBanners
import com.filimonov.afishamovies.di.ModeQualifier
import com.filimonov.afishamovies.di.listpagecomponent.IdQualifier
import com.filimonov.afishamovies.domain.enums.Category
import com.filimonov.afishamovies.domain.usecases.GetMediaBannersByCategoryFromLocalUseCase
import com.filimonov.afishamovies.domain.usecases.GetMediaBannersByCategoryFromRemoteUseCase
import com.filimonov.afishamovies.domain.usecases.GetMediaBannersByCollectionUseCase
import com.filimonov.afishamovies.domain.usecases.GetPersonsUseCase
import com.filimonov.afishamovies.domain.usecases.GetSimilarMoviesUseCase
import com.filimonov.afishamovies.presentation.ui.listpage.mediabannergridadapter.ListPageMedia
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class ListPageViewModel @Inject constructor(
    private val getMediaBannersByCategoryFromLocalUseCase: GetMediaBannersByCategoryFromLocalUseCase,
    private val getMediaBannersByCategoryFromRemoteUseCase: GetMediaBannersByCategoryFromRemoteUseCase,
    private val getPersonsUseCase: GetPersonsUseCase,
    private val getSimilarMoviesUseCase: GetSimilarMoviesUseCase,
    private val getMediaBannersByCollectionUseCase: GetMediaBannersByCollectionUseCase,
    @IdQualifier private val id: Int,
    @ModeQualifier private val mode: ListPageMode
) : ViewModel() {

    private var page = 1

    private var currentList = mutableListOf<ListPageMedia>()

    private val _state: MutableStateFlow<ListPageState> = MutableStateFlow(ListPageState.Initial)

    val state = _state.asStateFlow()

    init {
        loadData()
    }

    private fun loadData() {
        when (mode) {
            ListPageMode.MEDIA -> loadMedia()

            ListPageMode.ACTOR -> loadActors()

            ListPageMode.WORKER -> loadWorkers()

            ListPageMode.SIMILAR_MOVIES -> loadSimilarMovies()

            ListPageMode.COLLECTION -> loadCollection()
        }
    }

    fun nextPage() {
        if (_state.value is ListPageState.Loading) return

        viewModelScope.launch {
            try {
                _state.value = ListPageState.Loading(currentList + ListPageMedia.Loading)
                val category = Category.entries[id]
                val newList = getMediaBannersByCategoryFromRemoteUseCase(page + 1, category)
                    .toListPageMediaBannerList()
                if (newList.isNotEmpty()) {
                    page++
                    currentList.addAll(newList)
                }
                _state.value = ListPageState.Success(currentList)
            } catch (e: CancellationException) {
                throw e
            } catch (_: Exception) {
                _state.value = ListPageState.Error(currentList + ListPageMedia.Error)
            }
        }
    }

    private fun loadMedia() {
        val mediasFromCache =
            getMediaBannersByCategoryFromLocalUseCase(Category.entries[id])
        _state.value = ListPageState.Success(mediasFromCache.toListPageMediaBannerList())
            .also { currentList.addAll(it.mediaBanners) }
    }

    private fun loadActors() {
        val actors = getPersonsUseCase(id).filter { it.character != null }
        _state.value = ListPageState.Success(actors.toListPageMediaActorBanners())
    }

    private fun loadWorkers() {
        val workers = getPersonsUseCase(id).filter { it.character == null }
        _state.value = ListPageState.Success(workers.toListPageMediaWorkerBanners())
    }

    private fun loadSimilarMovies() {
        val similarMovies = getSimilarMoviesUseCase(id)
        _state.value = ListPageState.Success(similarMovies.toListPageMediaBannerList())
    }

    private fun loadCollection() {
        viewModelScope.launch {
            getMediaBannersByCollectionUseCase(id)
                .collect {
                    _state.value = ListPageState.Success(it.toListPageMediaBannerList())
                }
        }
    }
}