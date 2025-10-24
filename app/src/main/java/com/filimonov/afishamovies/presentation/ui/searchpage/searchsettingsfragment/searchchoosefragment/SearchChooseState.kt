package com.filimonov.afishamovies.presentation.ui.searchpage.searchsettingsfragment.searchchoosefragment

sealed class SearchChooseState {

    data class Initial(val list: List<String>) : SearchChooseState()

    data class Search(val list: List<String>) : SearchChooseState()
}