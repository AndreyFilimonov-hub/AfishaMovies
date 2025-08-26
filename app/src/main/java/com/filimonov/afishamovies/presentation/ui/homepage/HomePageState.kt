package com.filimonov.afishamovies.presentation.ui.homepage

sealed class HomePageState {

    data object Loading: HomePageState()

    data class Success(
        val categories: List<MediaSection>
    ) : HomePageState()

    data object Error : HomePageState()
}