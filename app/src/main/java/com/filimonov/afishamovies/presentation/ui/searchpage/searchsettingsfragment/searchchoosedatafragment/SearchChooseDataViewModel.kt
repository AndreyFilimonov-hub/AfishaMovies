package com.filimonov.afishamovies.presentation.ui.searchpage.searchsettingsfragment.searchchoosedatafragment

import android.icu.util.Calendar
import androidx.lifecycle.ViewModel
import com.filimonov.afishamovies.di.SelectedYearFromQualifier
import com.filimonov.afishamovies.di.SelectedYearToQualifier
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class SearchChooseDataViewModel @Inject constructor(
    @SelectedYearFromQualifier private val selectedYearFrom: Int?,
    @SelectedYearToQualifier private val selectedYearTo: Int?
) : ViewModel() {

    private val currentYear = Calendar.getInstance().get(Calendar.YEAR)
    private val startYear = currentYear - 11

    private val yearsFrom = findRightDateRange(selectedYearFrom)

    private val yearsTo = findRightDateRange(selectedYearTo)

    private val _state = MutableStateFlow<SearchChooseDataState>(
        SearchChooseDataState.Success(
            selectedYearFrom,
            yearsFrom,
            "${yearsFrom.first()} - ${yearsFrom.last()}",
            selectedYearTo,
            yearsTo,
            "${yearsTo.first()} - ${yearsTo.last()}"
        )
    )
    val state = _state.asStateFlow()

    private fun findRightDateRange(findYear: Int?): MutableList<Int> {
        if (findYear != null) {
            var currentList = (startYear..currentYear).toMutableList()
            if (findYear < startYear) {
                while (findYear !in currentList) {
                    currentList = currentList.map { it - 11 }.toMutableList()
                }
                return currentList
            } else {
                while (findYear !in currentList) {
                    currentList = currentList.map { it + 11 }.toMutableList()
                }
                return currentList
            }
        } else {
            return (startYear..currentYear).toMutableList()
        }
    }

    fun nextStep(dataRangeType: DateRangeType) {
        when (dataRangeType) {
            DateRangeType.FROM -> {
                _state.update { state ->
                    when (state) {
                        is SearchChooseDataState.Success -> state.copy(yearsFrom = yearsFrom.map { it + 11 })
                    }
                }
            }

            DateRangeType.TO -> {
                _state.update { state ->
                    when (state) {
                        is SearchChooseDataState.Success -> state.copy(yearsTo = yearsTo.map { it + 11 })
                    }
                }
            }
        }
    }

    fun previousStep(dataRangeType: DateRangeType) {
        when (dataRangeType) {
            DateRangeType.FROM -> {
                _state.update { state ->
                    when (state) {
                        is SearchChooseDataState.Success -> state.copy(yearsFrom = yearsFrom.map { it - 11 })
                    }
                }
            }

            DateRangeType.TO -> {
                _state.update { state ->
                    when (state) {
                        is SearchChooseDataState.Success -> state.copy(yearsTo = yearsTo.map { it - 11 })
                    }
                }
            }
        }
    }
}