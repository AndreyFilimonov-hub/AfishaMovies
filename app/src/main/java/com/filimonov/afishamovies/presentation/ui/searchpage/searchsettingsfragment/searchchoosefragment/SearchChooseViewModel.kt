package com.filimonov.afishamovies.presentation.ui.searchpage.searchsettingsfragment.searchchoosefragment

import android.content.Context
import androidx.lifecycle.ViewModel
import com.filimonov.afishamovies.di.searchpagecomponent.searchsettingscomponent.searchchoosecomponent.ChooseResIdQualifier
import com.filimonov.afishamovies.di.ModeQualifier
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class SearchChooseViewModel @Inject constructor(
    @ModeQualifier private val mode: FilterMode,
    @ChooseResIdQualifier var chooseResId: Int,
    private val applicationContext: Context
) : ViewModel() {

    private val list: List<ChooseItem> = if (mode == FilterMode.GENRE) {
        Genres.entries
    } else {
        Countries.entries
    }

    private val _state = MutableStateFlow<SearchChooseState>(SearchChooseState.Initial(list))
    val state = _state.asStateFlow()

    fun sendRequest(query: String) {
        val filteredList = list.filter { applicationContext.getString(it.itemResId).contains(query.lowercase()) }
        _state.value = SearchChooseState.Search(filteredList)
    }
}