package com.filimonov.afishamovies.di.filmpagecomponent.addfilmpagetocollectioncomponent

import com.filimonov.afishamovies.di.MovieIdQualifier
import dagger.Module
import dagger.Provides

@Module
class AddFilmPageToCollectionModule {

    @Provides
    fun provideMovieId(@MovieIdQualifier movieId: Int): Int {
        return movieId
    }
}