package com.filimonov.afishamovies.presentation.ui.searchpage.searchsettingsfragment.searchchoosedatafragment

sealed class SearchChooseDataState {

    data class Success(
        val yearsFrom: List<Int>,
        val rangeFrom: String,
        val yearsTo: List<Int>,
        val rangeTo: String
    ) : SearchChooseDataState()
}