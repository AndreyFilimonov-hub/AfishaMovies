package com.filimonov.afishamovies.presentation.ui.listpage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.filimonov.afishamovies.data.repository.MediaBannerRepositoryImpl
import com.filimonov.afishamovies.domain.entities.MediaBannerEntity
import com.filimonov.afishamovies.domain.enum.Category
import com.filimonov.afishamovies.domain.usecases.GetMediaListByCategoryUseCase
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ListPageViewModel(
    private val categoryId: Int
) : ViewModel() {

    private val repository = MediaBannerRepositoryImpl

    private val getMediaListByCategoryUseCase = GetMediaListByCategoryUseCase(repository)

    private var page = 1

    private var currentList = mutableListOf<MediaBannerEntity>()

    private val _state: MutableStateFlow<ListPageState> = MutableStateFlow(
        ListPageState.Success(
            repository.getMediaBannersByCategory(categoryId)
        ).also { currentList = it.mediaBanners.toMutableList() }
    )

    val state = _state.asStateFlow()

    fun nextPage() {
        if (_state.value is ListPageState.Loading) return

        viewModelScope.launch {
            try {
                _state.value = ListPageState.Loading(currentList)
                val category = Category.entries.first { it.id == categoryId }
                val newList = getMediaListByCategoryUseCase(page + 1, category)
                if (newList.isNotEmpty()) {
                    page++
                    currentList.addAll(newList)
                }
                _state.value = ListPageState.Success(currentList)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _state.value = ListPageState.Error(currentList)
            }
        }
    }
}