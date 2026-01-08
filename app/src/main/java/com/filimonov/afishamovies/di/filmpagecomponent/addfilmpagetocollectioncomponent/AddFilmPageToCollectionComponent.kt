package com.filimonov.afishamovies.di.filmpagecomponent.addfilmpagetocollectioncomponent

import com.filimonov.afishamovies.presentation.ui.filmpage.bottomsheetfragment.AddFilmPageToCollectionBottomSheetFragment
import dagger.Subcomponent

@Subcomponent(modules = [AddFilmPageToCollectionViewModelModule::class, AddFilmPageToCollectionModule::class])
interface AddFilmPageToCollectionComponent {

    fun inject(fragment: AddFilmPageToCollectionBottomSheetFragment)

    @Subcomponent.Factory
    interface Factory {

        fun create(): AddFilmPageToCollectionComponent
    }
}