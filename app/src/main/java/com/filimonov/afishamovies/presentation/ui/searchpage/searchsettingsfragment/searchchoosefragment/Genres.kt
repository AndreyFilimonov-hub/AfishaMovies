package com.filimonov.afishamovies.presentation.ui.searchpage.searchsettingsfragment.searchchoosefragment

import com.filimonov.afishamovies.R

enum class Genres(override val itemResId: Int) : ChooseItem {

    ANY(R.string.any),
    COMEDY(R.string.comedy),
    MELODRAMA(R.string.melodrama),
    ACTION(R.string.action),
    WESTERN(R.string.western),
    DRAMA(R.string.drama)
}