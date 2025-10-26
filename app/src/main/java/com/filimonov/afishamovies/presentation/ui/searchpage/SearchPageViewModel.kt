@file:OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)

package com.filimonov.afishamovies.presentation.ui.searchpage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.filimonov.afishamovies.domain.usecases.GetMoviesByQueryUseCase
import com.filimonov.afishamovies.domain.usecases.GetPersonsByQueryUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
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
    private val getMoviesByQueryUseCase: GetMoviesByQueryUseCase,
    private val getPersonsByQueryUseCase: GetPersonsByQueryUseCase
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
                            val searchItems = coroutineScope {
                                val medias =
                                    async { getMoviesByQueryUseCase(page = page, query = query) }
                                val persons =
                                    async { getPersonsByQueryUseCase(page = page, query = query) }

                                val searchMediaBanners =
                                    medias.await()
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
                                        .filter { it.year in yearFrom..yearTo }
                                        .filter {
                                            it.rating?.toFloatOrNull()
                                                ?.let { rating -> rating in ratingFrom..ratingTo } == true
                                        }
                                        .filter {
                                            if (isDontWatched != null && isDontWatched == true) {
                                                !it.isWatched
                                            } else {
                                                return@filter true
                                            }
                                        }
                                        .sortedByDescending {
                                            when (sortType) {
                                                SortType.DATE -> it.year
                                                SortType.POPULAR -> it.votes
                                                SortType.RATING -> it.rating?.toInt()
                                            }
                                        }
                                        .map { SearchItem.MediaBanner(it) }
                                val searchPersonBanners =
                                    persons.await()
                                        .filter {
                                            it.name?.isNotEmpty() == true
                                        }
                                        .map { SearchItem.PersonBanner(it) }

                                mutableListOf<SearchItem>().apply {
                                    addAll(searchMediaBanners)
                                    addAll(searchPersonBanners)
                                }.toList()
                            }

                            if (searchItems.isEmpty()) {
                                emit(SearchPageState.Empty)
                            } else {
                                emit(SearchPageState.Success(searchItems))
                            }
                        } catch (_: Exception) {
                            emit(SearchPageState.Error)
                        }
                    }
                }
                .collect { _state.value = it }
        }
    }

    fun sendRequest(
        query: String,
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
        _query.value = query
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