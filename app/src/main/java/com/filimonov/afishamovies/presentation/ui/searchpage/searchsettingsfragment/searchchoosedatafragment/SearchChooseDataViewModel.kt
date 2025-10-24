package com.filimonov.afishamovies.presentation.ui.searchpage.searchsettingsfragment.searchchoosedatafragment

import android.icu.util.Calendar
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.filimonov.afishamovies.di.SelectedYearFromQualifier
import com.filimonov.afishamovies.di.SelectedYearToQualifier
import javax.inject.Inject

class SearchChooseDataViewModel @Inject constructor(
    @SelectedYearFromQualifier private val selectedYearFrom: Int?,
    @SelectedYearToQualifier private val selectedYearTo: Int?
) : ViewModel() {

    private val currentYear = Calendar.getInstance().get(Calendar.YEAR)
    private val startYear = currentYear - 11

    private val yearsFrom = findRightDateRange(selectedYearFrom)

    private val yearsTo = findRightDateRange(selectedYearTo)

    private val _yearsFromLd = MutableLiveData<List<Int>>(yearsFrom)

    val yearsFromLd: LiveData<List<Int>>
        get() = _yearsFromLd

    private val _yearsToLd = MutableLiveData<List<Int>>(yearsTo)
    val yearsToLd: LiveData<List<Int>>
        get() = _yearsToLd

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
                _yearsFromLd.value = _yearsFromLd.value?.map { it + 11 }
            }

            DateRangeType.TO -> {
                _yearsToLd.value = _yearsToLd.value?.map { it + 11 }
            }
        }
    }

    fun previousStep(dataRangeType: DateRangeType) {
        when (dataRangeType) {
            DateRangeType.FROM -> {
                _yearsFromLd.value = _yearsFromLd.value?.map { it - 11 }
            }

            DateRangeType.TO -> {
                _yearsToLd.value = _yearsToLd.value?.map { it - 11 }
            }
        }
    }
}