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

                    _state.value = ProfilePageState.Success(
                        watchedList.take(10),
                        collectionList,
                        interestedList.take(10),
                        watchedList.size,
                        interestedList.size
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