package com.filimonov.afishamovies.domain.repository

import com.filimonov.afishamovies.domain.entities.FilmPageEntity

interface FilmPageRepository {

    suspend fun getFilmPageById(id: Int): FilmPageEntity
}