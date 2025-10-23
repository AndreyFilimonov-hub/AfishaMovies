package com.filimonov.afishamovies.presentation.ui.searchpage.searchsettingsfragment.searchchoosefragment

import android.util.Log
import androidx.lifecycle.ViewModel
import com.filimonov.afishamovies.di.ModeQualifier
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class SearchChooseViewModel @Inject constructor(@ModeQualifier private val mode: FilterMode) : ViewModel() {

    private val list = if (mode == FilterMode.GENRE) {
        mutableListOf(
            "Комедия",
            "Мелодрама",
            "Боевик",
            "Вестерн",
            "Драма"
        )
    } else {
        mutableListOf(
            "Россия",
            "Великобритания",
            "Германия",
            "США",
            "Франция"
        )
    }

    private val _state = MutableStateFlow<SearchChooseState>(SearchChooseState.Initial(list))
    val state = _state.asStateFlow()

    fun sendRequest(query: String) {
        val filteredList = list.filter { it.lowercase().contains(query.lowercase()) }
        _state.value = SearchChooseState.Search(filteredList)
        Log.d("AAA", filteredList.toString())
    }
}