package com.filimonov.afishamovies.presentation.ui.homepage.sectionadapter

import androidx.annotation.StringRes
import com.filimonov.afishamovies.presentation.ui.homepage.mediabannerhorizontaladapter.HomePageMedia

data class MediaSection(
    val categoryId: Int,
    @StringRes
    val title: Int,
    val mediaList: List<HomePageMedia>
)