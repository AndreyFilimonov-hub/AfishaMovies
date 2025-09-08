package com.filimonov.afishamovies.presentation.ui.listpage

import com.filimonov.afishamovies.presentation.ui.listpage.mediabannergridadapter.ListPageMedia

sealed class ListPageState {

    data class Success(
        val mediaBanners: List<ListPageMedia>
    ) : ListPageState()

    data class Error(
        val currentList: List<ListPageMedia>
    ) : ListPageState()

    data class Loading(
        val currentList: List<ListPageMedia>
    ) : ListPageState()
}