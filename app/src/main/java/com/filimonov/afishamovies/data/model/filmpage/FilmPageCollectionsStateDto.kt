package com.filimonov.afishamovies.data.model.filmpage

data class FilmPageCollectionsStateDto (
    val isLiked: Boolean,
    val isWantToWatch: Boolean,
    val isWatched: Boolean
)