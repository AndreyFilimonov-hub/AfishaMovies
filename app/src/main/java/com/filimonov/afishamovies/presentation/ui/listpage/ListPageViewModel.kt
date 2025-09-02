package com.filimonov.afishamovies.presentation.ui.listpage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.filimonov.afishamovies.data.repository.MediaBannerRepositoryImpl
import com.filimonov.afishamovies.domain.entities.MediaBannerEntity
import com.filimonov.afishamovies.domain.usecases.GetActionUSAMovieListUseCase
import com.filimonov.afishamovies.domain.usecases.GetComedyRussiaMovieListUseCase
import com.filimonov.afishamovies.domain.usecases.GetDramaFranceMovieListUseCase
import com.filimonov.afishamovies.domain.usecases.GetPopularMovieListUseCase
import com.filimonov.afishamovies.domain.usecases.GetSeriesListUseCase
import com.filimonov.afishamovies.domain.usecases.GetTop250MovieListUseCase
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ListPageViewModel(
    private val categoryId: Int
) : ViewModel() {

    private val repository = MediaBannerRepositoryImpl

    private val getComedyRussiaMovieListUseCase = GetComedyRussiaMovieListUseCase(repository)
    private val getPopularMovieListUseCase = GetPopularMovieListUseCase(repository)
    private val getActionUSAMovieListUseCase = GetActionUSAMovieListUseCase(repository)
    private val getTop250MovieListUseCase = GetTop250MovieListUseCase(repository)
    private val getDramaFranceMovieListUseCase = GetDramaFranceMovieListUseCase(repository)
    private val getSeriesListUseCase = GetSeriesListUseCase(repository)

    private val useCases = mapOf<Int, suspend (Int) -> List<MediaBannerEntity>>(
        MediaBannerRepositoryImpl.COMEDY_RUSSIAN to { page -> getComedyRussiaMovieListUseCase(page) },
        MediaBannerRepositoryImpl.POPULAR to { page -> getPopularMovieListUseCase(page) },
        MediaBannerRepositoryImpl.ACTION_USA to { page -> getActionUSAMovieListUseCase(page) },
        MediaBannerRepositoryImpl.TOP250 to { page -> getTop250MovieListUseCase(page) },
        MediaBannerRepositoryImpl.DRAMA_FRANCE to { page -> getDramaFranceMovieListUseCase(page) },
        MediaBannerRepositoryImpl.SERIES to { page -> getSeriesListUseCase(page) },
    )

    private var page = 1

    private val _state: MutableStateFlow<ListPageState> = MutableStateFlow(
        ListPageState.Success(
            repository.getMediaBannersByCategory(categoryId)
        )
    )
    val state = _state.asStateFlow()

    fun nextPage() {
        val useCase = useCases[categoryId] ?: return

        viewModelScope.launch {
            try {
                val currentList = (_state.value as ListPageState.Success)
                    .mediaBanners
                    .toMutableList()
                val newList = useCase(++page)
                currentList.addAll(newList)
                _state.value = ListPageState.Success(currentList)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _state.value = ListPageState.Error
            }
        }
    }
}