package com.filimonov.afishamovies.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.filimonov.afishamovies.data.repository.MediaRepositoryImpl
import com.filimonov.afishamovies.domain.entities.Media
import com.filimonov.afishamovies.domain.usecases.GetActionUSAMovieListUseCase
import com.filimonov.afishamovies.domain.usecases.GetComedyRussiaMovieListUseCase
import com.filimonov.afishamovies.domain.usecases.GetDramaFranceMovieListUseCase
import com.filimonov.afishamovies.domain.usecases.GetPopularMovieListUseCase
import com.filimonov.afishamovies.domain.usecases.GetSeriesListUseCase
import com.filimonov.afishamovies.domain.usecases.GetTop250MovieListUseCase
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class HomePageViewModel : ViewModel() {

    private val repository = MediaRepositoryImpl()

    private val getComedyRussiaMovieListUseCase = GetComedyRussiaMovieListUseCase(repository)
    private val getPopularMovieListUseCase = GetPopularMovieListUseCase(repository)
    private val getActionUSAMovieListUseCase = GetActionUSAMovieListUseCase(repository)
    private val getTop250MovieListUseCase = GetTop250MovieListUseCase(repository)
    private val getDramaFranceMovieListUseCase = GetDramaFranceMovieListUseCase(repository)
    private val getSeriesListUseCase = GetSeriesListUseCase(repository)


    private val _comedyRussian: MutableLiveData<List<Media>> = MutableLiveData()
    val comedyRussian: LiveData<List<Media>>
        get() = _comedyRussian

    private val _popularMovies: MutableLiveData<List<Media>> = MutableLiveData()
    val popularMovies: LiveData<List<Media>>
        get() = _popularMovies

    private val _actionUSA: MutableLiveData<List<Media>> = MutableLiveData()
    val actionUSA: LiveData<List<Media>>
        get() = _actionUSA

    private val _top250: MutableLiveData<List<Media>> = MutableLiveData()
    val top250: LiveData<List<Media>>
        get() = _top250

    private val _dramaFrance: MutableLiveData<List<Media>> = MutableLiveData()
    val dramaFrance: LiveData<List<Media>>
        get() = _dramaFrance

    private val _series: MutableLiveData<List<Media>> = MutableLiveData()
    val series: LiveData<List<Media>>
        get() = _series

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            try {
                _comedyRussian.value = getComedyRussiaMovieListUseCase()
            } catch (e: IOException) {
                Log.d("HomePageViewModel", e.toString())
            } catch (e: HttpException) {
                Log.d("HomePageViewModel", e.toString())
            }
        }
        viewModelScope.launch {
            try {
                _popularMovies.value = getPopularMovieListUseCase()
            } catch (e: IOException) {
                Log.d("HomePageViewModel", e.toString())
            } catch (e: HttpException) {
                Log.d("HomePageViewModel", e.toString())
            }
        }
        viewModelScope.launch {
            try {
                _actionUSA.value = getActionUSAMovieListUseCase()
            } catch (e: IOException) {
                Log.d("HomePageViewModel", e.toString())
            } catch (e: HttpException) {
                Log.d("HomePageViewModel", e.toString())
            }
        }
        viewModelScope.launch {
            try {
                _top250.value = getTop250MovieListUseCase()
            } catch (e: IOException) {
                Log.d("HomePageViewModel", e.toString())
            } catch (e: HttpException) {
                Log.d("HomePageViewModel", e.toString())
            }
        }
        viewModelScope.launch {
            try {
                _dramaFrance.value = getDramaFranceMovieListUseCase()
            } catch (e: IOException) {
                Log.d("HomePageViewModel", e.toString())
            } catch (e: HttpException) {
                Log.d("HomePageViewModel", e.toString())
            }
        }
        viewModelScope.launch {
            try {
                _series.value = getSeriesListUseCase()
            } catch (e: IOException) {
                Log.d("HomePageViewModel", e.toString())
            } catch (e: HttpException) {
                Log.d("HomePageViewModel", e.toString())
            }
        }
    }
}