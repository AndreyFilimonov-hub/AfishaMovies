package com.filimonov.afishamovies.presentation.ui.searchpage.searchsettingsfragment

import android.app.Application
import androidx.lifecycle.ViewModel
import com.filimonov.afishamovies.R
import com.filimonov.afishamovies.di.searchpagecomponent.searchsettingscomponent.CountryQualifier
import com.filimonov.afishamovies.di.searchpagecomponent.searchsettingscomponent.GenreQualifier
import com.filimonov.afishamovies.di.searchpagecomponent.searchsettingscomponent.IsDontWatchedQualifier
import com.filimonov.afishamovies.di.searchpagecomponent.searchsettingscomponent.RatingFromQualifier
import com.filimonov.afishamovies.di.searchpagecomponent.searchsettingscomponent.RatingToQualifier
import com.filimonov.afishamovies.di.searchpagecomponent.searchsettingscomponent.ShowTypeQualifier
import com.filimonov.afishamovies.di.searchpagecomponent.searchsettingscomponent.SortTypeQualifier
import com.filimonov.afishamovies.di.searchpagecomponent.searchsettingscomponent.YearFromQualifier
import com.filimonov.afishamovies.di.searchpagecomponent.searchsettingscomponent.YearToQualifier
import com.filimonov.afishamovies.presentation.ui.searchpage.ShowType
import com.filimonov.afishamovies.presentation.ui.searchpage.SortType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class SearchSettingsViewModel @Inject constructor(
    @ShowTypeQualifier var showType: ShowType,
    @SortTypeQualifier var sortType: SortType,
    @CountryQualifier var country: String?,
    @GenreQualifier var genre: String?,
    @YearFromQualifier var yearFrom: Int,
    @YearToQualifier var yearTo: Int,
    @RatingFromQualifier var ratingFrom: Float,
    @RatingToQualifier var ratingTo: Float,
    @IsDontWatchedQualifier var isDontWatched: Boolean,
    private val appContext: Application
) : ViewModel() {

    private val _state = MutableStateFlow<SearchSettingsState>(
        SearchSettingsState.Success(
            showType,
            sortType,
            country,
            genre,
            getYearRange(yearFrom, yearTo),
            getRatingRange(ratingFrom, ratingTo),
            listOf(ratingFrom, ratingTo),
            isDontWatched
        )
    )
    val state = _state.asStateFlow()

    fun updateShowType(showType: ShowType) {
        this.showType = showType
        _state.update { currentState ->
            when (currentState) {
                is SearchSettingsState.Success -> {
                    currentState.copy(showType = showType)
                }
            }
        }
    }

    fun updateCountry(country: String) {
        _state.update { currentState ->
            when (currentState) {
                is SearchSettingsState.Success -> {
                    this.country = country
                    currentState.copy(country = country)
                }
            }
        }
    }

    fun updateGenre(genre: String) {
        _state.update { currentState ->
            when (currentState) {
                is SearchSettingsState.Success -> {
                    this.genre = genre
                    currentState.copy(genre = genre)
                }
            }
        }
    }

    fun updateYearRange(yearFrom: Int, yearTo: Int) {
        _state.update { currentState ->
            when (currentState) {
                is SearchSettingsState.Success -> {
                    this.yearFrom = yearFrom
                    this.yearTo = yearTo
                    val period = getYearRange(yearFrom, yearTo)
                    currentState.copy(yearRange = period)
                }
            }
        }
    }

    fun updateRangeSlider(ratingFrom: Float, ratingTo: Float) {
        _state.update { currentState ->
            when (currentState) {
                is SearchSettingsState.Success -> {
                    this.ratingFrom = ratingFrom
                    this.ratingTo = ratingTo
                    val ratingRange = getRatingRange(ratingFrom, ratingTo)
                    currentState.copy(ratingRange = ratingRange, ratingValues = listOf(ratingFrom, ratingTo))
                }
            }
        }
    }

    fun updateSortType(sortType: SortType) {
        this.sortType = sortType
        _state.update { currentState ->
            when (currentState) {
                is SearchSettingsState.Success -> {
                    currentState.copy(sortType = sortType)
                }
            }
        }
    }

    fun updateIsDontWatched(isDontWatched: Boolean) {
        this.isDontWatched = isDontWatched
        _state.update { currentState ->
            when (currentState) {
                is SearchSettingsState.Success -> {
                    currentState.copy(isDontWatched = isDontWatched)
                }
            }
        }
    }

    fun reset() {
        showType = ShowType.ALL
        sortType = SortType.DATE
        country = null
        genre = null
        yearFrom = Int.MIN_VALUE
        yearTo = Int.MAX_VALUE
        ratingFrom = RATING_FROM_DEFAULT
        ratingTo = RATING_TO_DEFAULT
        isDontWatched = false
        _state.value = SearchSettingsState.Success(
            showType,
            sortType,
            country,
            genre,
            getYearRange(yearFrom, yearTo),
            getRatingRange(ratingFrom, ratingTo),
            listOf(RATING_FROM_DEFAULT, RATING_TO_DEFAULT),
            false
        )
    }

    private fun getYearRange(yearFrom: Int, yearTo: Int): String {
        return if (yearFrom != Int.MIN_VALUE && yearTo != Int.MAX_VALUE && yearFrom != yearTo) {
            "с $yearFrom до $yearTo"
        } else if (yearFrom == yearTo) {
            yearFrom.toString()
        } else {
            appContext.getString(R.string.any)
        }
    }

    private fun getRatingRange(ratingFrom: Float, ratingTo: Float): String {
        return if (ratingFrom == RATING_FROM_DEFAULT && ratingTo == RATING_TO_DEFAULT) {
            appContext.getString(R.string.any)
        } else if (ratingFrom == ratingTo) {
            "${ratingFrom.toInt()}"
        } else {
            "${ratingFrom.toInt()} - ${ratingTo.toInt()}"
        }
    }

    companion object {

        private const val RATING_FROM_DEFAULT = 1f
        private const val RATING_TO_DEFAULT = 10f
    }
}