package com.filimonov.afishamovies.presentation.ui.searchpage.searchsettingsfragment

import com.filimonov.afishamovies.presentation.ui.searchpage.ShowType
import com.filimonov.afishamovies.presentation.ui.searchpage.SortType

sealed class SearchSettingsState {

    data class Success(
        val showType: ShowType,
        val sortType: SortType,
        val country: String?,
        val genre: String?,
        val yearRange: String,
        val ratingRange: String,
        val ratingValues: List<Float>,
        val isDontWatched: Boolean
    ) : SearchSettingsState()
}