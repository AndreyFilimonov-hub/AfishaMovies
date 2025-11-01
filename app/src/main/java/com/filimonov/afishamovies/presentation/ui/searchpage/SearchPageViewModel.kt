@file:OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)

package com.filimonov.afishamovies.presentation.ui.searchpage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.filimonov.afishamovies.domain.entities.SearchMediaBannerEntity
import com.filimonov.afishamovies.domain.usecases.GetMoviesByQueryUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchPageViewModel @Inject constructor(
    private val getMoviesByQueryUseCase: GetMoviesByQueryUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<SearchPageState>(SearchPageState.Initial)
    val state = _state.asStateFlow()

    private val _query = MutableStateFlow("")

    private var page = 1

    private var showType: ShowType = ShowType.ALL
    private var country: String? = null
    private var genre: String? = null
    private var yearFrom: Int = Int.MIN_VALUE
    private var yearTo: Int = Int.MAX_VALUE
    private var ratingFrom: Float = 1f
    private var ratingTo: Float = 10f
    private var sortType: SortType = SortType.DATE
    private var isDontWatched: Boolean? = null

    private var currentList = mutableListOf<SearchItem.MediaBanner>()

    init {
        viewModelScope.launch {
            _query
                .debounce(500)
                .map { it.trim() }
                .filter { it.isNotEmpty() }
                .distinctUntilChanged()
                .flatMapLatest { query ->
                    flow {
                        emit(SearchPageState.Loading)
                        try {
                            val medias = getMoviesByQueryUseCase(page = page, query = query)

                            val filteredMediaBannerList = filterList(medias)

                            if (filteredMediaBannerList.isEmpty()) {
                                emit(SearchPageState.Empty)
                            } else {
                                emit(SearchPageState.Success(filteredMediaBannerList))
                                currentList = filteredMediaBannerList.toMutableList()
                            }
                        } catch (_: Exception) {
                            emit(SearchPageState.Error)
                        }
                    }
                }
                .collect { _state.value = it }
        }
    }

    fun updateList() {
        viewModelScope.launch {
            try {
                _state.value = SearchPageState.Loading

                val medias = getMoviesByQueryUseCase(page, _query.value)

                val filteredMediaBannerList = filterList(medias)

                if (filteredMediaBannerList.isEmpty()) {
                    _state.value = SearchPageState.Empty
                } else {
                    _state.value = SearchPageState.Success(filteredMediaBannerList)
                    currentList = filteredMediaBannerList.toMutableList()
                }
            } catch (_: Exception) {
                _state.value = SearchPageState.Error
            }
        }
    }

    fun sendRequest(query: String) {
        _query.value = query
    }

    private fun filterList(list: List<SearchMediaBannerEntity>): List<SearchItem.MediaBanner> {
        return list
            .asSequence()
            .filter { it.name.isNotEmpty() }
            .filter {
                when (showType) {
                    ShowType.ALL -> return@filter true
                    ShowType.FILM -> !it.isSeries
                    ShowType.SERIES -> it.isSeries
                }
            }
            .filter {
                if (country != null) {
                    it.countries?.contains(country) == true
                } else {
                    return@filter true
                }
            }
            .filter {
                if (genre != null) {
                    it.genres?.contains(genre) == true
                } else {
                    return@filter true
                }
            }
            .filter { it.year in this.yearFrom..this.yearTo }
            .filter {
                it.rating?.toFloatOrNull()
                    ?.let { rating -> rating in this.ratingFrom..this.ratingTo } == true
            }
            .filter {
                if (this.isDontWatched != null && this.isDontWatched == true) {
                    !it.isWatched
                } else {
                    return@filter true
                }
            }
            .sortedByDescending {
                when (sortType) {
                    SortType.DATE -> it.year.toFloat()
                    SortType.POPULAR -> it.votes?.toFloat()
                    SortType.RATING -> it.rating?.toFloat()
                }
            }
            .map { SearchItem.MediaBanner(it) }
            .toList()
    }

    fun setupFilters(
        showType: ShowType,
        country: String?,
        genre: String?,
        yearFrom: Int?,
        yearTo: Int?,
        ratingFrom: Float?,
        ratingTo: Float?,
        sortType: SortType,
        isDontWatched: Boolean?
    ) {
        this.showType = showType
        this.country = country
        this.genre = genre
        this.yearFrom = yearFrom ?: Int.MIN_VALUE
        this.yearTo = yearTo ?: Int.MAX_VALUE
        this.ratingFrom = ratingFrom ?: 1f
        this.ratingTo = ratingTo ?: 10f
        this.sortType = sortType
        this.isDontWatched = isDontWatched
    }
}