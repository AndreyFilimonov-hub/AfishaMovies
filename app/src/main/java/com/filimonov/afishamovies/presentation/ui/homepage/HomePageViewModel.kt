package com.filimonov.afishamovies.presentation.ui.homepage

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.filimonov.afishamovies.R
import com.filimonov.afishamovies.data.repository.MediaBannerRepositoryImpl
import com.filimonov.afishamovies.domain.enum.Category
import com.filimonov.afishamovies.domain.usecases.GetMediaListByCategoryUseCase
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomePageViewModel : ViewModel() {

    private val repository = MediaBannerRepositoryImpl

    private val getMediaListByCategoryUseCase = GetMediaListByCategoryUseCase(repository)

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
                        MediaSection(
                            Category.COMEDY_RUSSIAN.id,
                            R.string.russian_comedy,
                            getMediaListByCategoryUseCase(category = Category.COMEDY_RUSSIAN)
                        ),
                        MediaSection(
                            Category.POPULAR.id,
                            R.string.popular,
                            getMediaListByCategoryUseCase(category = Category.POPULAR)
                        ),
                        MediaSection(
                            Category.ACTION_USA.id,
                            R.string.action_usa,
                            getMediaListByCategoryUseCase(category = Category.ACTION_USA)
                        ),
                        MediaSection(
                            Category.TOP250.id,
                            R.string.top250,
                            getMediaListByCategoryUseCase(category = Category.TOP250)
                        ),
                        MediaSection(
                            Category.DRAMA_FRANCE.id,
                            R.string.drama_france,
                            getMediaListByCategoryUseCase(category = Category.DRAMA_FRANCE)
                        ),
                        MediaSection(
                            Category.SERIES.id,
                            R.string.series,
                            getMediaListByCategoryUseCase(category = Category.SERIES)
                        )
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