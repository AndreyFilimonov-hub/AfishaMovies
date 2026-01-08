package com.filimonov.afishamovies.di.filmpagecomponent.addfilmpagetocollectioncomponent

import androidx.lifecycle.ViewModel
import com.filimonov.afishamovies.di.ViewModelKey
import com.filimonov.afishamovies.presentation.ui.filmpage.bottomsheetfragment.AddFilmPageToCollectionViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface AddFilmPageToCollectionViewModelModule {

    @IntoMap
    @ViewModelKey(AddFilmPageToCollectionViewModel::class)
    @Binds
    fun bindAddFilmPageToCollectionViewModel(viewModel: AddFilmPageToCollectionViewModel): ViewModel
}