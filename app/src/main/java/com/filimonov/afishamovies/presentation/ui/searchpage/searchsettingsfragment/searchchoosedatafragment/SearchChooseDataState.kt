package com.filimonov.afishamovies.presentation.ui.searchpage.searchsettingsfragment.searchchoosedatafragment

sealed class SearchChooseDataState {

    data class Success(
        val selectedYearFrom: Int?,
        val yearsFrom: List<Int>,
        val rangeFrom: String,
        val selectedYearTo: Int?,
        val yearsTo: List<Int>,
        val rangeTo: String
    ) : SearchChooseDataState()
}