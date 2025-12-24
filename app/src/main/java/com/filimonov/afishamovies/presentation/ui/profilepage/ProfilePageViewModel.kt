package com.filimonov.afishamovies.presentation.ui.profilepage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.filimonov.afishamovies.domain.enums.DefaultCollection
import com.filimonov.afishamovies.domain.usecases.ClearCollectionUseCase
import com.filimonov.afishamovies.domain.usecases.CreateCollectionUseCase
import com.filimonov.afishamovies.domain.usecases.DeleteCollectionUseCase
import com.filimonov.afishamovies.domain.usecases.GetCollectionIdByKeyUseCase
import com.filimonov.afishamovies.domain.usecases.GetCollectionsUseCase
import com.filimonov.afishamovies.domain.usecases.GetMediaBannersByCollectionUseCase
import com.filimonov.afishamovies.presentation.ui.profilepage.mediabanneradapter.MediaBannerModel
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProfilePageViewModel @Inject constructor(
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
            try {
                coroutineScope {
                    val watchedListDeferred = async {
                        watchedCollectionId =
                            getCollectionIdByKeyUseCase(DefaultCollection.WATCHED.key)
                        getMediaBannersByCollectionUseCase(watchedCollectionId)
                    }
                    val interestedListDeferred = async {
                        interestedCollectionId =
                            getCollectionIdByKeyUseCase(DefaultCollection.INTERESTED.key)
                        getMediaBannersByCollectionUseCase(interestedCollectionId)
                    }
                    val collectionListDeferred = async { getCollectionsUseCase() }

                    val watchedList = watchedListDeferred.await()
                    val collectionList = collectionListDeferred.await()
                    val interestedList = interestedListDeferred.await()

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

                    _state.value = ProfilePageState.Success(
                        watchedListMapped,
                        collectionList,
                        interestedListMapped,
                        watchedListSize,
                        interestedListSize
                    )
                }
            } catch (_: Exception) {
                _state.value = ProfilePageState.Error
            }
        }
    }

    fun clearCollection(collectionKey: DefaultCollection) {
        viewModelScope.launch {
            when (collectionKey) {
                DefaultCollection.WATCHED -> clearCollectionUseCase(watchedCollectionId)
                DefaultCollection.INTERESTED -> clearCollectionUseCase(interestedCollectionId)
                DefaultCollection.LIKED,
                DefaultCollection.WANT_TO_WATCH -> {
                    // не чистятся
                }
            }
        }
    }

    fun createCollection(name: String) {
        viewModelScope.launch {
            createCollectionUseCase(name)
        }
    }

    fun deleteCollection(collectionId: Int) {
        viewModelScope.launch {
            deleteCollectionUseCase(collectionId)
        }
    }
}