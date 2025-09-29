package com.filimonov.afishamovies.presentation.ui.gallery

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.filimonov.afishamovies.di.MovieIdQualifier
import com.filimonov.afishamovies.domain.enums.TypeImage
import com.filimonov.afishamovies.domain.usecases.GetImagesByMovieIdUseCase
import com.filimonov.afishamovies.presentation.ui.gallery.imageadapter.GalleryModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class GalleryViewModel @Inject constructor(
    private val getImagesByMovieIdUseCase: GetImagesByMovieIdUseCase,
    @MovieIdQualifier private val movieId: Int
) : ViewModel() {

    private val _state: MutableStateFlow<GalleryState> =
        MutableStateFlow(GalleryState.InitialLoading)

    val state = _state.asStateFlow()

    private var imagesByType: MutableMap<TypeImage, List<GalleryModel>> = mutableMapOf()
    private var isLastPageByType: MutableMap<TypeImage, Boolean> = mutableMapOf()

    private var page = 1

    private var currentType: TypeImage = TypeImage.FRAME

    init {
        initialLoadData()
    }

    private fun initialLoadData() {
        viewModelScope.launch {
            try {
                _state.value = GalleryState.InitialLoading
                coroutineScope {
                    val frames = async {
                        getImagesByMovieIdUseCase(
                            movieId = movieId,
                            type = TypeImage.FRAME
                        )
                    }
                    val backstageImages = async {
                        getImagesByMovieIdUseCase(
                            movieId = movieId,
                            type = TypeImage.BACKSTAGE
                        )
                    }
                    val posters = async {
                        getImagesByMovieIdUseCase(
                            movieId = movieId,
                            type = TypeImage.POSTER
                        )
                    }

                    imagesByType = mutableMapOf(
                        TypeImage.FRAME to frames.await().map { GalleryModel.Image(it) },
                        TypeImage.BACKSTAGE to backstageImages.await()
                            .map { GalleryModel.Image(it) },
                        TypeImage.POSTER to posters.await().map { GalleryModel.Image(it) }
                    )

                    isLastPageByType = mutableMapOf(
                        TypeImage.FRAME to false,
                        TypeImage.BACKSTAGE to false,
                        TypeImage.POSTER to false,
                    )

                    _state.value = GalleryState.Success(
                        imagesByType[currentType] ?: emptyList(),
                        currentType,
                        isLastPageByType[currentType] ?: false
                    )
                }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _state.value = GalleryState.InitialError
            }
        }
    }

    fun selectType(type: TypeImage) {
        if (type == currentType) {
            return
        }

        currentType = type

        if (imagesByType.isEmpty()) {
            _state.value = GalleryState.InitialError
        } else {
            _state.value = GalleryState.Success(
                imagesByType[currentType] ?: emptyList(),
                currentType,
                isLastPageByType[currentType] ?: false
            )
        }
    }

    fun loadData() {
        if (imagesByType.isEmpty()) {
            initialLoadData()
        } else {
            nextPage()
        }
    }

    private fun nextPage() {
        if (_state.value is GalleryState.Loading) return

        viewModelScope.launch {

            try {
                _state.value = GalleryState.Loading(
                    imagesByType[currentType]?.plus(GalleryModel.Loading) ?: emptyList()
                )

                val photos = getImagesByMovieIdUseCase(
                    page = page + 1,
                    movieId,
                    currentType
                ).map { GalleryModel.Image(it) }

                if (photos.isNotEmpty()) {
                    page++
                    val oldList = imagesByType[currentType]?.toMutableList() ?: mutableListOf()
                    oldList.addAll(photos)
                    imagesByType[currentType] = oldList
                } else {
                    isLastPageByType[currentType] = true
                }

                _state.value = GalleryState.Success(
                    imagesByType[currentType] ?: emptyList(),
                    currentType,
                    isLastPageByType[currentType] ?: false
                )
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _state.value = GalleryState.Error(
                    imagesByType[currentType]?.plus(GalleryModel.Error)
                        ?: listOf(GalleryModel.Error)
                )
            }
        }
    }
}