package com.filimonov.afishamovies.presentation.ui.searchpage

sealed class SearchPageState {

    data object Initial : SearchPageState()

    data object Loading : SearchPageState()

    data class Success(val result: List<SearchItem>) : SearchPageState()

    data object Error : SearchPageState()

    data object Empty : SearchPageState()
}