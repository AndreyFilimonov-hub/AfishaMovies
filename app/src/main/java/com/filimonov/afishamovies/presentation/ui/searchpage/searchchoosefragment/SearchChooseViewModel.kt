package com.filimonov.afishamovies.presentation.ui.searchpage.searchchoosefragment

import androidx.lifecycle.ViewModel

class SearchChooseViewModel : ViewModel() {

    fun getListOfCountries() = listOf(
        "Россия",
        "Великобритания",
        "Германия",
        "США",
        "Франция"
    )

    fun getListOfGenres() = listOf(
        "Комедия",
        "Мелодрама",
        "Боевик",
        "Вестерн",
        "Драма"
    )
}