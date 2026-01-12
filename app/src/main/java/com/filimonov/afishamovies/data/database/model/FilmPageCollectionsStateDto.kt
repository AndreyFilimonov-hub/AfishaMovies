package com.filimonov.afishamovies.data.database.model

data class FilmPageCollectionsStateDto (
    val isLiked: Boolean,
    val isWantToWatch: Boolean,
    val isWatched: Boolean
)