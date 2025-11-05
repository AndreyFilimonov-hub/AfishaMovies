package com.filimonov.afishamovies.di.filmpagecomponent

import androidx.lifecycle.ViewModel
import com.filimonov.afishamovies.di.ViewModelKey
import com.filimonov.afishamovies.presentation.ui.filmpage.FilmPageViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface FilmPageViewModelModule {

    @IntoMap
    @ViewModelKey(FilmPageViewModel::class)
    @Binds
    fun bindFilmPageViewModel(viewModel: FilmPageViewModel): ViewModel
}