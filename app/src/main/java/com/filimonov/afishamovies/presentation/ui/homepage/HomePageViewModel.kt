package com.filimonov.afishamovies.presentation.ui.homepage

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.filimonov.afishamovies.R
import com.filimonov.afishamovies.data.repository.MediaBannerRepositoryImpl
import com.filimonov.afishamovies.domain.enum.Category
import com.filimonov.afishamovies.domain.usecases.GetMediaListByCategoryUseCase
import com.filimonov.afishamovies.presentation.ui.homepage.mediabannerhorizontaladapter.HomePageMedia
import com.filimonov.afishamovies.presentation.ui.homepage.sectionadapter.MediaSection
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
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
                val sections = coroutineScope {
                    val categories = listOf(
                        Category.COMEDY_RUSSIAN to R.string.russian_comedy,
                        Category.POPULAR to R.string.popular,
                        Category.ACTION_USA to R.string.action_usa,
                        Category.TOP250 to R.string.top250,
                        Category.DRAMA_FRANCE to R.string.drama_france,
                        Category.SERIES to R.string.series,
                    )

                    categories.map { (category, titleResId) ->
                        async {
                            val banners = getMediaListByCategoryUseCase(category = category)
                            MediaSection(
                                category.id,
                                titleResId,
                                banners.map {
                                    HomePageMedia.Banner(it)
                                } + HomePageMedia.ShowAll
                            )
                        }
                    }.awaitAll()
                }

                _state.value = HomePageState.Success(sections)

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