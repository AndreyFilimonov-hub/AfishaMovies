package com.filimonov.afishamovies.presentation.ui.searchpage.searchsettingsfragment.searchchoosedatafragment

import android.icu.util.Calendar
import androidx.lifecycle.ViewModel
import com.filimonov.afishamovies.di.searchpagecomponent.searchsettingscomponent.searchchoosedatacomponent.SelectedYearFromQualifier
import com.filimonov.afishamovies.di.searchpagecomponent.searchsettingscomponent.searchchoosedatacomponent.SelectedYearToQualifier
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class SearchChooseDataViewModel @Inject constructor(
    @SelectedYearFromQualifier var selectedYearFrom: Int?,
    @SelectedYearToQualifier var selectedYearTo: Int?
) : ViewModel() {

    private val currentYear = Calendar.getInstance().get(Calendar.YEAR)
    private val startYear = currentYear - ELEVEN_ELEMENTS

    private var yearsFrom = findRightDateRange(selectedYearFrom)

    private var yearsTo = findRightDateRange(selectedYearTo)

    private val _state = MutableStateFlow<SearchChooseDataState>(
        SearchChooseDataState.Success(
            yearsFrom,
            "${yearsFrom.first()} - ${yearsFrom.last()}",
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
                    currentList = currentList.map { it - ELEVEN_ELEMENTS }.toMutableList()
                }
                return currentList
            } else {
                while (findYear !in currentList) {
                    currentList = currentList.map { it + ELEVEN_ELEMENTS }.toMutableList()
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
                        is SearchChooseDataState.Success -> {
                            yearsFrom = yearsFrom.map { it + ELEVEN_ELEMENTS }.toMutableList()
                            val rangeFrom = "${yearsFrom.first()} - ${yearsFrom.last()}"
                            state.copy(yearsFrom = yearsFrom, rangeFrom = rangeFrom)
                        }
                    }
                }
            }

            DateRangeType.TO -> {
                _state.update { state ->
                    when (state) {
                        is SearchChooseDataState.Success -> {
                            yearsTo = yearsTo.map { it + ELEVEN_ELEMENTS }.toMutableList()
                            val rangeTo = "${yearsTo.first()} - ${yearsTo.last()}"
                            state.copy(yearsTo = yearsTo, rangeTo = rangeTo)
                        }
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
                        is SearchChooseDataState.Success -> {
                            yearsFrom = yearsFrom.map { it - ELEVEN_ELEMENTS }.toMutableList()
                            val rangeFrom = "${yearsFrom.first()} - ${yearsFrom.last()}"
                            state.copy(yearsFrom = yearsFrom, rangeFrom = rangeFrom)
                        }
                    }
                }
            }

            DateRangeType.TO -> {
                _state.update { state ->
                    when (state) {
                        is SearchChooseDataState.Success -> {
                            yearsTo = yearsTo.map { it - ELEVEN_ELEMENTS }.toMutableList()
                            val rangeTo = "${yearsTo.first()} - ${yearsTo.last()}"
                            state.copy(yearsTo = yearsTo, rangeTo = rangeTo)
                        }
                    }
                }
            }
        }
    }

    companion object {

        private const val ELEVEN_ELEMENTS = 11
    }
}