package com.filimonov.afishamovies.presentation.ui.filmpage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.filimonov.afishamovies.di.MovieIdQualifier
import com.filimonov.afishamovies.domain.entities.FilmPageEntity
import com.filimonov.afishamovies.domain.entities.MediaBannerEntity
import com.filimonov.afishamovies.domain.entities.PersonBannerEntity
import com.filimonov.afishamovies.domain.enums.DefaultCollection
import com.filimonov.afishamovies.domain.usecases.AddFilmPageToDbUseCase
import com.filimonov.afishamovies.domain.usecases.AddMediaBannerToCollectionUseCase
import com.filimonov.afishamovies.domain.usecases.AddMediaBannerToInterestedCollectionUseCase
import com.filimonov.afishamovies.domain.usecases.ClearCachedPersonListAndSimilarMoviesUseCase
import com.filimonov.afishamovies.domain.usecases.DeleteMediaBannerFromCollectionUseCase
import com.filimonov.afishamovies.domain.usecases.DeleteUnusedFilmPageUseCase
import com.filimonov.afishamovies.domain.usecases.GetCollectionIdByKeyUseCase
import com.filimonov.afishamovies.domain.usecases.GetFilmPageByIdUseCase
import com.filimonov.afishamovies.domain.usecases.GetImagePreviewListByMovieIdUseCase
import com.filimonov.afishamovies.domain.usecases.GetMediaBannerByIdFromLocalUseCase
import com.filimonov.afishamovies.domain.usecases.UpdateDefaultCategoriesFlagsUseCase
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class FilmPageViewModel @Inject constructor(
    @MovieIdQualifier private val movieId: Int,
    private val addMediaBannerToInterestedCollectionUseCase: AddMediaBannerToInterestedCollectionUseCase,
    private val addMediaBannerToCollectionUseCase: AddMediaBannerToCollectionUseCase,
    private val getCollectionIdByKeyUseCase: GetCollectionIdByKeyUseCase,
    private val getMediaBannerByIdFromLocalUseCase: GetMediaBannerByIdFromLocalUseCase,
    private val getFilmPageByIdUseCase: GetFilmPageByIdUseCase,
    private val getImagePreviewListByMovieIdUseCase: GetImagePreviewListByMovieIdUseCase,
    private val clearCachedPersonListAndSimilarMoviesUseCase: ClearCachedPersonListAndSimilarMoviesUseCase,
    private val addFilmPageToDbUseCase: AddFilmPageToDbUseCase,
    private val deleteMediaBannerFromCollectionUseCase: DeleteMediaBannerFromCollectionUseCase,
    private val deleteUnusedFilmPageUseCase: DeleteUnusedFilmPageUseCase,
    private val updateDefaultCategoriesFlagsUseCase: UpdateDefaultCategoriesFlagsUseCase
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
                    val imagePreviews = async {
                        try {
                            getImagePreviewListByMovieIdUseCase(movieId)
                        } catch (_: Exception) {
                            null
                        }
                    }

                    _state.value =
                        FilmPageState.Success(filmPage.await(), imagePreviews.await()?.take(10))

                    saveMediaBannerInInterestedCollection()
                }
            } catch (e: CancellationException) {
                throw e
            } catch (_: Exception) {
                _state.value = FilmPageState.Error
            }
        }
    }

    private fun saveFilmPageToDb(filmPageEntity: FilmPageEntity) {
        viewModelScope.launch {
            addFilmPageToDbUseCase(filmPageEntity)
        }
    }

    private fun saveMediaBannerInInterestedCollection() {
        viewModelScope.launch {
            val mediaBannerEntity = getMediaBannerByIdFromLocalUseCase(movieId)
            addMediaBannerToInterestedCollectionUseCase(mediaBannerEntity)
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

    private fun addMediaBannerToDefaultCollection(collection: DefaultCollection) {
        viewModelScope.launch {
            val collectionId = getCollectionIdByKeyUseCase(collection.key)
            val mediaBannerEntity = getMediaBannerByIdFromLocalUseCase(movieId)
            addMediaBannerToCollectionUseCase(mediaBannerEntity, collectionId)
        }
    }

    private fun deleteFromCollection(
        collectionId: Int? = null,
        collection: DefaultCollection
    ) {
        viewModelScope.launch {
            if (collectionId == null) {
                val collectionIdFromDb = getCollectionIdByKeyUseCase(collection.key)
                deleteMediaBannerFromCollectionUseCase(movieId, collectionIdFromDb)
            } else {
                deleteMediaBannerFromCollectionUseCase(movieId, collectionId)
            }
            deleteUnusedFilmPageUseCase()
        }
    }

    fun toggleLike() {
        val currentState = _state.value
        if (currentState !is FilmPageState.Success) return

        val updatedEntity = currentState.filmPage.copy(isLiked = !currentState.filmPage.isLiked)
        _state.value = currentState.copy(filmPage = updatedEntity)

        viewModelScope.launch {
            if (updatedEntity.isLiked) {
                saveFilmPageToDb(updatedEntity)
                addMediaBannerToDefaultCollection(DefaultCollection.LIKED)
            } else {
                deleteFromCollection(collection = DefaultCollection.LIKED)
            }
            updateDefaultCategoriesFlagsUseCase(
                filmId = updatedEntity.id,
                isLiked = updatedEntity.isLiked
            )
        }
    }

    fun toggleWantToWatch() {
        val currentState = _state.value
        if (currentState !is FilmPageState.Success) return

        val updatedEntity =
            currentState.filmPage.copy(isWantToWatch = !currentState.filmPage.isWantToWatch)
        _state.value = currentState.copy(filmPage = updatedEntity)

        viewModelScope.launch {
            if (updatedEntity.isWantToWatch) {
                saveFilmPageToDb(updatedEntity)
                addMediaBannerToDefaultCollection(DefaultCollection.WANT_TO_WATCH)
            } else {
                deleteFromCollection(collection = DefaultCollection.WANT_TO_WATCH)
            }
            updateDefaultCategoriesFlagsUseCase(
                filmId = updatedEntity.id,
                isWantToWatch = updatedEntity.isWantToWatch
            )
        }
    }

    fun toggleWatched() {
        val currentState = _state.value
        if (currentState !is FilmPageState.Success) return

        val updatedEntity = currentState.filmPage.copy(isWatched = !currentState.filmPage.isWatched)
        _state.value = currentState.copy(filmPage = updatedEntity)

        viewModelScope.launch {
            if (updatedEntity.isWatched) {
                saveFilmPageToDb(updatedEntity)
                addMediaBannerToDefaultCollection(DefaultCollection.WATCHED)
            } else {
                deleteFromCollection(collection = DefaultCollection.WATCHED)
            }
            updateDefaultCategoriesFlagsUseCase(
                filmId = updatedEntity.id,
                isWatched = updatedEntity.isWatched
            )
        }
    }

    override fun onCleared() {
        super.onCleared()
        clearCachedPersonListAndSimilarMoviesUseCase(movieId)
    }
}