package com.filimonov.afishamovies.presentation.ui.filmpage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.filimonov.afishamovies.di.MovieIdQualifier
import com.filimonov.afishamovies.domain.entities.MediaBannerEntity
import com.filimonov.afishamovies.domain.entities.PersonBannerEntity
import com.filimonov.afishamovies.domain.usecases.ClearCachedPersonListUseCase
import com.filimonov.afishamovies.domain.usecases.GetFilmPageByIdUseCase
import com.filimonov.afishamovies.domain.usecases.GetImagePreviewListByMovieIdUseCase
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class FilmPageViewModel @Inject constructor(
    @MovieIdQualifier private val movieId: Int,
    private val getFilmPageByIdUseCase: GetFilmPageByIdUseCase,
    private val getImagePreviewListByMovieIdUseCase: GetImagePreviewListByMovieIdUseCase,
    private val clearCachedPersonListUseCase: ClearCachedPersonListUseCase
) : ViewModel() {


    private val _state: MutableStateFlow<FilmPageState> = MutableStateFlow(FilmPageState.Loading)

    val state = _state.asStateFlow()

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            try {
                coroutineScope {
                    val filmPage = async { getFilmPageByIdUseCase(movieId) }
                    val imagePreviews = async { getImagePreviewListByMovieIdUseCase(movieId) }

                    _state.value = FilmPageState.Success(filmPage.await(), imagePreviews.await().take(10))
                }
            } catch (e: CancellationException) {
                throw e
            } catch (_: Exception) {
                _state.value = FilmPageState.Error
            }
        }
    }

    fun getFirst10SimilarMovies(): List<MediaBannerEntity>? {
        return (_state.value as FilmPageState.Success).filmPage.similarMovies
            ?.take(10)
    }

    fun getFirst20Actors(): List<PersonBannerEntity> {
        return (_state.value as FilmPageState.Success).filmPage.persons
            .filter { it.character != null }
            .take(20)
    }

    fun getFirst10Workers(): List<PersonBannerEntity> {
        return (_state.value as FilmPageState.Success).filmPage.persons
            .filter { it.character == null }
            .take(10)
    }

    fun actorsCount(): String {
        return (_state.value as FilmPageState.Success).filmPage
            .persons
            .filter { it.character != null }
            .size
            .toString()
    }

    fun workersCount(): String {
        return (_state.value as FilmPageState.Success).filmPage
            .persons
            .filter { it.character == null }
            .size
            .toString()
    }

    override fun onCleared() {
        super.onCleared()
        clearCachedPersonListUseCase(movieId)
    }
}