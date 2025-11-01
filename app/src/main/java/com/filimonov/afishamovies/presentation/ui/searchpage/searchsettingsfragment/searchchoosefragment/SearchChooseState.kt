package com.filimonov.afishamovies.presentation.ui.searchpage.searchsettingsfragment.searchchoosefragment

sealed class SearchChooseState {

    data class Initial(val list: List<ChooseItem>) : SearchChooseState()

    data class Search(val list: List<ChooseItem>) : SearchChooseState()
}