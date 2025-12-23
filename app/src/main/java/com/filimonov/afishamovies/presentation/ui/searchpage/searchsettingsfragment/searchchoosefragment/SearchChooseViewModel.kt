package com.filimonov.afishamovies.presentation.ui.searchpage.searchsettingsfragment.searchchoosefragment

import android.app.Application
import androidx.lifecycle.ViewModel
import com.filimonov.afishamovies.di.ModeQualifier
import com.filimonov.afishamovies.di.searchpagecomponent.searchsettingscomponent.searchchoosecomponent.ChooseItemQualifier
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class SearchChooseViewModel @Inject constructor(
    @ModeQualifier private val mode: FilterMode,
    @ChooseItemQualifier var chooseItem: String?,
    private val appContext: Application
) : ViewModel() {

    private val list: List<ChooseItem> = if (mode == FilterMode.GENRE) {
        Genres.entries.filter { it != Genres.ANY }
    } else {
        Countries.entries.filter { it != Countries.ANY }
    }

    private val _state = MutableStateFlow<SearchChooseState>(SearchChooseState.Initial(list))
    val state = _state.asStateFlow()

    fun sendRequest(query: String) {
        val filteredList = list.filter { appContext.getString(it.itemResId).contains(query.lowercase()) }
        _state.value = SearchChooseState.Search(filteredList)
    }
}