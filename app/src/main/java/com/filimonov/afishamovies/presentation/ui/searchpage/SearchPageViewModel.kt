@file:OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)

package com.filimonov.afishamovies.presentation.ui.searchpage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.filimonov.afishamovies.domain.usecases.GetMoviesByQueryUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
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

    private val _query = MutableSharedFlow<String>(1)

    private var page = 1

    var showType: ShowType = ShowType.ALL
    var country: String? = null
    var genre: String? = null
    var yearFrom: Int = Int.MIN_VALUE
    var yearTo: Int = Int.MAX_VALUE
    var ratingFrom: Float = 1f
    var ratingTo: Float = 10f
    var sortType: SortType = SortType.DATE
    var isDontWatched: Boolean = false

    private var currentList = mutableListOf<SearchItem.MediaBanner>()

    init {
        viewModelScope.launch {
            _query.emit("")

            _query
                .debounce(500)
                .map { it.trim() }
                .flatMapLatest { query ->
                    flow {
                        emit(SearchPageState.Loading)
                        try {
                            val medias = getMoviesByQueryUseCase(
                                page = page,
                                query = query
                            ).map { SearchItem.MediaBanner(it) }

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
        val filteredMediaBannerList = filterList(currentList)

        if (filteredMediaBannerList.isEmpty()) {
            _state.value = SearchPageState.Empty
        } else {
            _state.value = SearchPageState.Success(filteredMediaBannerList)
        }
    }

    fun sendRequest(query: String) {
        viewModelScope.launch {
            _query.emit(query)
        }
    }

    private fun filterList(list: List<SearchItem.MediaBanner>): List<SearchItem.MediaBanner> {
        return list
            .asSequence()
            .filter { it.mediaBanner.name.isNotEmpty() }
            .filter {
                when (showType) {
                    ShowType.ALL -> return@filter true
                    ShowType.FILM -> !it.mediaBanner.isSeries
                    ShowType.SERIES -> it.mediaBanner.isSeries
                }
            }
            .filter {
                if (country != null) {
                    it.mediaBanner.countries?.contains(country) == true
                } else {
                    return@filter true
                }
            }
            .filter {
                if (genre != null) {
                    it.mediaBanner.genres?.contains(genre) == true
                } else {
                    return@filter true
                }
            }
            .filter { it.mediaBanner.year in this.yearFrom..this.yearTo }
            .filter {
                it.mediaBanner.rating?.toFloatOrNull()
                    ?.let { rating -> rating in this.ratingFrom..this.ratingTo } == true
            }
            .filter {
                if (isDontWatched) {
                    !it.mediaBanner.isWatched
                } else {
                    return@filter true
                }
            }
            .sortedByDescending {
                when (sortType) {
                    SortType.DATE -> it.mediaBanner.year.toFloat()
                    SortType.POPULAR -> it.mediaBanner.votes?.toFloat()
                    SortType.RATING -> it.mediaBanner.rating?.toFloat()
                }
            }
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
        isDontWatched: Boolean
    ) {
        this.showType = showType
        this.country = country
        this.genre = genre
        this.yearFrom = yearFrom ?: Int.MIN_VALUE
        this.yearTo = yearTo ?: Int.MAX_VALUE
        this.ratingFrom = ratingFrom ?: RATING_FROM_DEFAULT
        this.ratingTo = ratingTo ?: RATING_TO_DEFAULT
        this.sortType = sortType
        this.isDontWatched = isDontWatched
    }

    companion object {

        private const val RATING_FROM_DEFAULT = 1f
        private const val RATING_TO_DEFAULT = 10f
    }
}