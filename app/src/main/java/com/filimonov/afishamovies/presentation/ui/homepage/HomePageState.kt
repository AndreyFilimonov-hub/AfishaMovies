package com.filimonov.afishamovies.presentation.ui.homepage

import com.filimonov.afishamovies.presentation.ui.homepage.sectionadapter.MediaSection

sealed class HomePageState {

    data object Loading: HomePageState()

    data class Success(
        val categories: List<MediaSection>
    ) : HomePageState()

    data object Error : HomePageState()
}