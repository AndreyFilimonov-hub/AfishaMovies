package com.filimonov.afishamovies.presentation.ui.searchpage.searchsettingsfragment.searchchoosefragment

import com.filimonov.afishamovies.R

enum class Countries(override val itemResId: Int) : ChooseItem {

    ANY(R.string.any_v2),
    RUSSIA(R.string.russia),
    GB(R.string.great_britain),
    GERMANY(R.string.germany),
    USA(R.string.usa),
    FRANCE(R.string.france)
}