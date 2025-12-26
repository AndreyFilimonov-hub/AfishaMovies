package com.filimonov.afishamovies.data.model.filmpage

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.filimonov.afishamovies.data.database.model.FilmPageDbModel
import com.filimonov.afishamovies.data.database.model.FilmPersonCrossRef
import com.filimonov.afishamovies.data.database.model.FilmSimilarMediaBannerCrossRef
import com.filimonov.afishamovies.data.database.model.MediaBannerDbModel
import com.filimonov.afishamovies.data.database.model.PersonDbModel

data class FilmPageDbDto(
    @Embedded
    val film: FilmPageDbModel,
    @Relation(
        parentColumn = "filmId",
        entityColumn = "personId",
        associateBy = Junction(FilmPersonCrossRef::class)
    )
    val persons: List<PersonDbModel>,
    @Relation(
        parentColumn = "filmId",
        entityColumn = "mediaBannerId",
        associateBy = Junction(FilmSimilarMediaBannerCrossRef::class)
    )
    val similarMovies: List<MediaBannerDbModel>
)