package com.filimonov.afishamovies.presentation.ui.filmpage.bottomsheetfragment

import android.app.Application
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.filimonov.afishamovies.R
import com.filimonov.afishamovies.di.MovieIdQualifier
import com.filimonov.afishamovies.domain.entities.CollectionWithMovieEntity
import com.filimonov.afishamovies.domain.entities.MediaBannerEntity
import com.filimonov.afishamovies.domain.enums.DefaultCollection
import com.filimonov.afishamovies.domain.usecases.AddMediaBannerToCollectionUseCase
import com.filimonov.afishamovies.domain.usecases.CreateCollectionUseCase
import com.filimonov.afishamovies.domain.usecases.DeleteMediaBannerFromCollectionUseCase
import com.filimonov.afishamovies.domain.usecases.GetAllCollectionsWithCountsByMediaBannerIdUseCase
import com.filimonov.afishamovies.domain.usecases.GetFilmPageByIdUseCase
import com.filimonov.afishamovies.domain.usecases.GetMediaBannerByIdFromLocalUseCase
import com.filimonov.afishamovies.domain.usecases.UpdateDefaultCategoriesFlagsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class AddFilmPageToCollectionViewModel @Inject constructor(
    @MovieIdQualifier private val movieId: Int,
    private val addMediaBannerToCollectionUseCase: AddMediaBannerToCollectionUseCase,
    private val createCollectionUseCase: CreateCollectionUseCase,
    private val deleteMediaBannerFromCollectionUseCase: DeleteMediaBannerFromCollectionUseCase,
    private val getMediaBannerByIdFromLocalUseCase: GetMediaBannerByIdFromLocalUseCase,
    private val getAllCollectionsWithCountsByMediaBannerIdUseCase: GetAllCollectionsWithCountsByMediaBannerIdUseCase,
    private val getFilmPageByIdUseCase: GetFilmPageByIdUseCase,
    private val updateDefaultCategoriesFlagsUseCase: UpdateDefaultCategoriesFlagsUseCase,
    private val appContext: Application
) : ViewModel() {

    private val _state =
        MutableStateFlow<AddFilmPageToCollectionState>(AddFilmPageToCollectionState.Initial)
    val state = _state.asStateFlow()

    private lateinit var mediaBannerEntity: MediaBannerEntity

    init {
        viewModelScope.launch {
            try {
                mediaBannerEntity = getMediaBannerByIdFromLocalUseCase(movieId)
                getAllCollectionsWithCountsByMediaBannerIdUseCase(mediaBannerEntity.id)
                    .collect { collectionList ->
                        _state.value =
                            AddFilmPageToCollectionState.Success(mediaBannerEntity, collectionList)
                    }
            } catch (_: Exception) {
                _state.value = AddFilmPageToCollectionState.Error
            }
        }
    }

    fun saveToCollections(collection: CollectionWithMovieEntity) {
        viewModelScope.launch {
            try {
                updateDefaultCategories(collection)
                addMediaBannerToCollectionUseCase(mediaBannerEntity, collection.id)
            } catch (e: Exception) {
                Log.d("AAA", e.toString())
            }
        }
    }

    fun deleteFromCollection(collection: CollectionWithMovieEntity) {
        viewModelScope.launch {
            try {
                updateDefaultCategories(collection)
                deleteMediaBannerFromCollectionUseCase(mediaBannerEntity.id, collection.id)
            } catch (_: Exception) {
            }
        }
    }

    private fun updateDefaultCategories(
        collection: CollectionWithMovieEntity
    ) {
        viewModelScope.launch {
            var updatedEntity = getFilmPageByIdUseCase(mediaBannerEntity.id)
            when (collection.name) {
                appContext.getString(R.string.liked) -> updatedEntity =
                    updatedEntity.copy(isLiked = !updatedEntity.isLiked)

                appContext.getString(R.string.want_to_watch) -> updatedEntity =
                    updatedEntity.copy(isWantToWatch = !updatedEntity.isWantToWatch)

                appContext.getString(R.string.watched) -> updatedEntity =
                    updatedEntity.copy(isWatched = !updatedEntity.isWatched)
            }
            updateDefaultCategoriesFlagsUseCase(
                movieId,
                updatedEntity.isLiked,
                updatedEntity.isWantToWatch,
                updatedEntity.isWatched
            )
        }
    }

    fun createCollection(name: String) {
        viewModelScope.launch {
            createCollectionUseCase(name, DefaultCollection.USER)
        }
    }
}