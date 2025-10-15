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
                                        .filter {
                                            it.name.isNotEmpty()

                                        }
                                        .sortedByDescending { it.votes }
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

    fun sendRequest(query: String) {
        _query.value = query
    }
}