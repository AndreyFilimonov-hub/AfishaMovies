package com.filimonov.afishamovies.di

import androidx.lifecycle.ViewModel
import com.filimonov.afishamovies.presentation.ui.gallery.GalleryViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface GalleryViewModelModule {

    @IntoMap
    @ViewModelKey(GalleryViewModel::class)
    @Binds
    fun bindViewModel(viewModel: GalleryViewModel): ViewModel
}