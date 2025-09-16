package com.filimonov.afishamovies.presentation.ui.filmpage

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.filimonov.afishamovies.di.MovieIdQualifier
import com.filimonov.afishamovies.domain.entities.PersonBannerEntity
import com.filimonov.afishamovies.domain.usecases.GetFilmPageByIdUseCase
import com.filimonov.afishamovies.domain.usecases.GetImagePreviewListByMovieId
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class FilmPageViewModel @Inject constructor(
    @MovieIdQualifier private val movieId: Int,
    private val getFilmPageByIdUseCase: GetFilmPageByIdUseCase,
    private val getImagePreviewListByMovieId: GetImagePreviewListByMovieId
) : ViewModel() {


    private val _state: MutableStateFlow<FilmPageState> = MutableStateFlow(FilmPageState.Loading)

    val state = _state.asStateFlow()

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            try {
                coroutineScope {
                    val filmPage = async { getFilmPageByIdUseCase(movieId) }
                    val imagePreviews = async { getImagePreviewListByMovieId(movieId) }

                    _state.value = FilmPageState.Success(filmPage.await(), imagePreviews.await())
                }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _state.value = FilmPageState.Error
                Log.d("AAA", e.toString())
            }
        }
    }

    fun getFirst20Actors(): List<PersonBannerEntity> {
        return (_state.value as FilmPageState.Success).filmPage.persons
            .filter { it.profession == PROFESSION_ACTOR }
            .take(20)
    }

    fun getFirst10Workers(): List<PersonBannerEntity> {
        return (_state.value as FilmPageState.Success).filmPage.persons
            .filter { it.profession != PROFESSION_ACTOR }
            .take(10)
    }

    fun actorsCount(): Int {
        return (_state.value as FilmPageState.Success).filmPage
            .persons
            .filter { it.profession == PROFESSION_ACTOR }
            .size
    }

    fun workersCount(): Int {
        return (_state.value as FilmPageState.Success).filmPage
            .persons
            .filter { it.profession != PROFESSION_ACTOR }
            .size
    }

    companion object {

        private const val PROFESSION_ACTOR = "актеры"
    }
}