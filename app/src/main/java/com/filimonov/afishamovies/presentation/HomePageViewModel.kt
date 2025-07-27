package com.filimonov.afishamovies.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.filimonov.afishamovies.data.repository.MediaRepositoryImpl
import com.filimonov.afishamovies.domain.entities.Movie
import com.filimonov.afishamovies.domain.entities.Series
import com.filimonov.afishamovies.domain.usecases.GetActionUSAMovieListUseCase
import com.filimonov.afishamovies.domain.usecases.GetComedyRussiaMovieListUseCase
import com.filimonov.afishamovies.domain.usecases.GetDramaFranceMovieListUseCase
import com.filimonov.afishamovies.domain.usecases.GetPopularMovieListUseCase
import com.filimonov.afishamovies.domain.usecases.GetSeriesListUseCase
import com.filimonov.afishamovies.domain.usecases.GetTop250MovieListUseCase
import kotlinx.coroutines.launch

class HomePageViewModel : ViewModel() {

    private val repository = MediaRepositoryImpl()

    private val getComedyRussiaMovieListUseCase = GetComedyRussiaMovieListUseCase(repository)
    private val getPopularMovieListUseCase = GetPopularMovieListUseCase(repository)
    private val getActionUSAMovieListUseCase = GetActionUSAMovieListUseCase(repository)
    private val getTop250MovieListUseCase = GetTop250MovieListUseCase(repository)
    private val getDramaFranceMovieListUseCase = GetDramaFranceMovieListUseCase(repository)
    private val getSeriesListUseCase = GetSeriesListUseCase(repository)


    private val _comedyRussian: MutableLiveData<List<Movie>> = MutableLiveData()
    val comedyRussian: LiveData<List<Movie>>
        get() = _comedyRussian

    private val _popularMovies: MutableLiveData<List<Movie>> = MutableLiveData()
    val popularMovies: LiveData<List<Movie>>
        get() = _popularMovies

    private val _actionUSA: MutableLiveData<List<Movie>> = MutableLiveData()
    val actionUSA: LiveData<List<Movie>>
        get() = _actionUSA

    private val _top250: MutableLiveData<List<Movie>> = MutableLiveData()
    val top250: LiveData<List<Movie>>
        get() = _top250

    private val _dramaFrance: MutableLiveData<List<Movie>> = MutableLiveData()
    val dramaFrance: LiveData<List<Movie>>
        get() = _dramaFrance

    private val _series: MutableLiveData<List<Series>> = MutableLiveData()
    val series: LiveData<List<Series>>
        get() = _series

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            try {
                _comedyRussian.value = getComedyRussiaMovieListUseCase()
                Log.d("AAA", "Comedy: " + comedyRussian.value.toString())
            } catch (e: Exception) {
                Log.d("AAA", "Comedy: $e")
            }
        }
        viewModelScope.launch {
            try {
                _popularMovies.value = getPopularMovieListUseCase()
                Log.d("AAA", "Popular: " + popularMovies.value.toString())
            } catch (e: Exception) {
                Log.d("AAA", "Popular: $e")
            }
        }
        viewModelScope.launch {
            try {
                _actionUSA.value = getActionUSAMovieListUseCase()
                Log.d("AAA","ActionUSA: " +  actionUSA.value.toString())
            } catch (e: Exception) {
                Log.d("AAA", "ActionUSA: $e")
            }
        }
        viewModelScope.launch {
            try {
                _top250.value = getTop250MovieListUseCase()
                Log.d("AAA","Top250: " +  top250.value.toString())
            } catch (e: Exception) {
                Log.d("AAA", "Top250: $e")
            }
        }
        viewModelScope.launch {
            try {
                _dramaFrance.value = getDramaFranceMovieListUseCase()
                Log.d("AAA","DramaFrance: " +  dramaFrance.value.toString())
            } catch (e: Exception) {
                Log.d("AAA", "DramaFrance: $e")
            }
        }
        viewModelScope.launch {
            try {
                _series.value = getSeriesListUseCase()
                Log.d("AAA","Series: " +  series.value.toString())
            } catch (e: Exception) {
                Log.d("AAA", "Series: $e")
            }
        }
    }
}