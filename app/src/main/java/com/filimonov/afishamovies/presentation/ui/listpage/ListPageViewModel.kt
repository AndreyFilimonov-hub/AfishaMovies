package com.filimonov.afishamovies.presentation.ui.listpage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.filimonov.afishamovies.data.mapper.toListPageMediaList
import com.filimonov.afishamovies.di.CategoryIdQualifier
import com.filimonov.afishamovies.domain.enums.Category
import com.filimonov.afishamovies.domain.usecases.GetMediaBannersByCategoryFromLocalUseCase
import com.filimonov.afishamovies.domain.usecases.GetMediaBannersByCategoryFromRemoteUseCase
import com.filimonov.afishamovies.presentation.ui.listpage.mediabannergridadapter.ListPageMedia
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class ListPageViewModel @Inject constructor(
    getMediaBannersByCategoryFromLocalUseCase: GetMediaBannersByCategoryFromLocalUseCase,
    private val getMediaBannersByCategoryFromRemoteUseCase: GetMediaBannersByCategoryFromRemoteUseCase,
    @CategoryIdQualifier private val categoryId: Int
) : ViewModel() {

    private var page = 1

    private var currentList = mutableListOf<ListPageMedia>()

    private val _state: MutableStateFlow<ListPageState> = MutableStateFlow(
        ListPageState.Success(
            getMediaBannersByCategoryFromLocalUseCase(Category.entries[categoryId]).toListPageMediaList()
        ).also { currentList = it.mediaBanners.toMutableList() }
    )

    val state = _state.asStateFlow()

    fun nextPage() {
        if (_state.value is ListPageState.Loading) return

        viewModelScope.launch {
            try {
                _state.value = ListPageState.Loading(currentList.map { it } + ListPageMedia.Loading)
                val category = Category.entries[categoryId]
                val newList = getMediaBannersByCategoryFromRemoteUseCase(page + 1, category)
                    .toListPageMediaList()
                if (newList.isNotEmpty()) {
                    page++
                    currentList.addAll(newList)
                }
                _state.value = ListPageState.Success(currentList.map { it })
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _state.value = ListPageState.Error(currentList.map { it } + ListPageMedia.Error)
            }
        }
    }
}