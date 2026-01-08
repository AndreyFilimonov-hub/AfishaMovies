package com.filimonov.afishamovies.di.filmpagecomponent

import com.filimonov.afishamovies.di.MovieIdQualifier
import com.filimonov.afishamovies.di.filmpagecomponent.addfilmpagetocollectioncomponent.AddFilmPageToCollectionComponent
import com.filimonov.afishamovies.presentation.ui.filmpage.FilmPageFragment
import dagger.BindsInstance
import dagger.Subcomponent

@Subcomponent(modules = [FilmPageViewModelModule::class])
interface FilmPageComponent {

    fun inject(fragment: FilmPageFragment)

    fun createAddFilmPageToCollectionComponent(): AddFilmPageToCollectionComponent.Factory

    @Subcomponent.Factory
    interface Factory {

        fun create(
            @BindsInstance @MovieIdQualifier movieId: Int
        ) : FilmPageComponent
    }
}