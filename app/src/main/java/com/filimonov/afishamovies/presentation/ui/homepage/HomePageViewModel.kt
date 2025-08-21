package com.filimonov.afishamovies.presentation.ui.homepage

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.filimonov.afishamovies.data.repository.HomePageRepositoryImpl
import com.filimonov.afishamovies.domain.entities.MediaBannerEntity
import com.filimonov.afishamovies.domain.usecases.GetActionUSAMovieListUseCase
import com.filimonov.afishamovies.domain.usecases.GetComedyRussiaMovieListUseCase
import com.filimonov.afishamovies.domain.usecases.GetDramaFranceMovieListUseCase
import com.filimonov.afishamovies.domain.usecases.GetPopularMovieListUseCase
import com.filimonov.afishamovies.domain.usecases.GetSeriesListUseCase
import com.filimonov.afishamovies.domain.usecases.GetTop250MovieListUseCase
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.net.UnknownHostException

class HomePageViewModel : ViewModel() {

    private val repository = HomePageRepositoryImpl()

    private val getComedyRussiaMovieListUseCase = GetComedyRussiaMovieListUseCase(repository)
    private val getPopularMovieListUseCase = GetPopularMovieListUseCase(repository)
    private val getActionUSAMovieListUseCase = GetActionUSAMovieListUseCase(repository)
    private val getTop250MovieListUseCase = GetTop250MovieListUseCase(repository)
    private val getDramaFranceMovieListUseCase = GetDramaFranceMovieListUseCase(repository)
    private val getSeriesListUseCase = GetSeriesListUseCase(repository)


    private val _comedyRussian: MutableLiveData<List<MediaBannerEntity>> = MutableLiveData()
    val comedyRussian: LiveData<List<MediaBannerEntity>>
        get() = _comedyRussian

    private val _popularMovies: MutableLiveData<List<MediaBannerEntity>> = MutableLiveData()
    val popularMovies: LiveData<List<MediaBannerEntity>>
        get() = _popularMovies

    private val _actionUSA: MutableLiveData<List<MediaBannerEntity>> = MutableLiveData()
    val actionUSA: LiveData<List<MediaBannerEntity>>
        get() = _actionUSA

    private val _top250: MutableLiveData<List<MediaBannerEntity>> = MutableLiveData()
    val top250: LiveData<List<MediaBannerEntity>>
        get() = _top250

    private val _dramaFrance: MutableLiveData<List<MediaBannerEntity>> = MutableLiveData()
    val dramaFrance: LiveData<List<MediaBannerEntity>>
        get() = _dramaFrance

    private val _series: MutableLiveData<List<MediaBannerEntity>> = MutableLiveData()
    val series: LiveData<List<MediaBannerEntity>>
        get() = _series

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            try {
                _comedyRussian.value = getComedyRussiaMovieListUseCase()
            } catch (_: HttpException) {
            } catch (_: UnknownHostException) {
            }
        }
        viewModelScope.launch {
            try {
                _popularMovies.value = getPopularMovieListUseCase()
            } catch (_: HttpException) {
            } catch (_: UnknownHostException) {
            }
        }
        viewModelScope.launch {
            try {
                _actionUSA.value = getActionUSAMovieListUseCase()
            } catch (_: HttpException) {
            } catch (_: UnknownHostException) {
            }
        }
        viewModelScope.launch {
            try {
                _top250.value = getTop250MovieListUseCase()
            } catch (_: HttpException) {
            } catch (_: UnknownHostException) {
            }
        }
        viewModelScope.launch {
            try {
                _dramaFrance.value = getDramaFranceMovieListUseCase()
            } catch (_: HttpException) {
            } catch (_: UnknownHostException) {
            }
        }
        viewModelScope.launch {
            try {
                _series.value = getSeriesListUseCase()
            } catch (_: HttpException) {
            } catch (_: UnknownHostException) {
            }
        }
    }
}