package com.filimonov.afishamovies.presentation.ui.homepage

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.filimonov.afishamovies.data.repository.MediaBannerRepositoryImpl
import com.filimonov.afishamovies.domain.usecases.GetActionUSAMovieListUseCase
import com.filimonov.afishamovies.domain.usecases.GetComedyRussiaMovieListUseCase
import com.filimonov.afishamovies.domain.usecases.GetDramaFranceMovieListUseCase
import com.filimonov.afishamovies.domain.usecases.GetPopularMovieListUseCase
import com.filimonov.afishamovies.domain.usecases.GetSeriesListUseCase
import com.filimonov.afishamovies.domain.usecases.GetTop250MovieListUseCase
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomePageViewModel : ViewModel() {

    private val repository = MediaBannerRepositoryImpl()

    private val getComedyRussiaMovieListUseCase = GetComedyRussiaMovieListUseCase(repository)
    private val getPopularMovieListUseCase = GetPopularMovieListUseCase(repository)
    private val getActionUSAMovieListUseCase = GetActionUSAMovieListUseCase(repository)
    private val getTop250MovieListUseCase = GetTop250MovieListUseCase(repository)
    private val getDramaFranceMovieListUseCase = GetDramaFranceMovieListUseCase(repository)
    private val getSeriesListUseCase = GetSeriesListUseCase(repository)

    private val _state = MutableStateFlow<HomePageState>(HomePageState.Loading)
    val state: StateFlow<HomePageState> = _state

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            try {
                _state.value = HomePageState.Success(
                    listOf(
                        MediaSection(0, "Русские комедии", getComedyRussiaMovieListUseCase()),
                        MediaSection(1, "Популярное", getPopularMovieListUseCase()),
                        MediaSection(2, "Боевики США", getActionUSAMovieListUseCase()),
                        MediaSection(3, "Топ 250", getTop250MovieListUseCase()),
                        MediaSection(4, "Драма Франции", getDramaFranceMovieListUseCase()),
                        MediaSection(5, "Сериалы", getSeriesListUseCase())
                    )
                )
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                Log.d("AAA", e.toString())
                _state.value = HomePageState.Error
            }
        }
    }

    fun reloadData() {
        _state.value = HomePageState.Loading
        loadData()
    }
}