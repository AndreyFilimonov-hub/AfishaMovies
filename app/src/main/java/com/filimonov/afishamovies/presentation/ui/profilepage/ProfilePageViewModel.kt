package com.filimonov.afishamovies.presentation.ui.profilepage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.filimonov.afishamovies.domain.entities.MediaBannerEntity
import com.filimonov.afishamovies.domain.enums.DefaultCollection
import com.filimonov.afishamovies.domain.usecases.AddMediaBannerToCollectionUseCase
import com.filimonov.afishamovies.domain.usecases.ClearCollectionUseCase
import com.filimonov.afishamovies.domain.usecases.CreateCollectionUseCase
import com.filimonov.afishamovies.domain.usecases.DeleteCollectionUseCase
import com.filimonov.afishamovies.domain.usecases.GetCollectionIdByKeyUseCase
import com.filimonov.afishamovies.domain.usecases.GetCollectionsUseCase
import com.filimonov.afishamovies.domain.usecases.GetMediaBannersByCollectionUseCase
import com.filimonov.afishamovies.presentation.ui.profilepage.mediabanneradapter.MediaBannerModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProfilePageViewModel @Inject constructor(
    private val addMediaBannerToCollectionUseCase: AddMediaBannerToCollectionUseCase,
    private val createCollectionUseCase: CreateCollectionUseCase,
    private val deleteCollectionUseCase: DeleteCollectionUseCase,
    private val getCollectionsUseCase: GetCollectionsUseCase,
    private val getMediaBannersByCollectionUseCase: GetMediaBannersByCollectionUseCase,
    private val getCollectionIdByKeyUseCase: GetCollectionIdByKeyUseCase,
    private val clearCollectionUseCase: ClearCollectionUseCase
) : ViewModel() {

    companion object {
        private const val UNDEFINED_ID = -1
    }

    private val _state = MutableStateFlow<ProfilePageState>(ProfilePageState.Loading)
    val state = _state.asStateFlow()

    private var watchedCollectionId: Int = UNDEFINED_ID
    private var interestedCollectionId: Int = UNDEFINED_ID

    init {
        viewModelScope.launch {
            watchedCollectionId =
                getCollectionIdByKeyUseCase(DefaultCollection.WATCHED.key)
            interestedCollectionId =
                getCollectionIdByKeyUseCase(DefaultCollection.INTERESTED.key)

            combine(
                getMediaBannersByCollectionUseCase(watchedCollectionId),
                getMediaBannersByCollectionUseCase(interestedCollectionId),
                getCollectionsUseCase()
            ) { watchedList, interestedList, collectionList ->
                val watchedListSize = if (watchedList.isEmpty()) {
                    ""
                } else {
                    watchedList.size.toString()
                }
                val interestedListSize = if (interestedList.isEmpty()) {
                    ""
                } else {
                    interestedList.size.toString()
                }

                val watchedListMapped = watchedList.take(10)
                    .map { MediaBannerModel.Banner(it) } + MediaBannerModel.ClearHistory
                val interestedListMapped = interestedList.take(10)
                    .map { MediaBannerModel.Banner(it) } + MediaBannerModel.ClearHistory

                ProfilePageState.Success(
                    watchedListMapped,
                    collectionList,
                    interestedListMapped,
                    watchedListSize,
                    interestedListSize
                )
            }
                .catch { _state.value = ProfilePageState.Error }
                .collect {
                    _state.value = it
                }
        }
    }

    fun clearCollection(collectionKey: DefaultCollection) {
        viewModelScope.launch {
            when (collectionKey) {
                DefaultCollection.WATCHED -> clearCollectionUseCase(watchedCollectionId)
                DefaultCollection.INTERESTED -> clearCollectionUseCase(interestedCollectionId)
                DefaultCollection.LIKED,
                DefaultCollection.USER,
                DefaultCollection.WANT_TO_WATCH -> {
                    // не чистятся
                }
            }
        }
    }

    fun createCollection(name: String, key: DefaultCollection) {
        viewModelScope.launch {
            createCollectionUseCase(name, key)
        }
    }

    fun deleteCollection(collectionId: Int) {
        viewModelScope.launch {
            deleteCollectionUseCase(collectionId)
        }
    }

    fun addMediaBannerToInterestedCollection(mediaBannerEntity: MediaBannerEntity) {
        viewModelScope.launch {
            addMediaBannerToCollectionUseCase(mediaBannerEntity, interestedCollectionId)
        }
    }
}