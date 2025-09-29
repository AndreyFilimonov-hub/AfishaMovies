package com.filimonov.afishamovies.di

import com.filimonov.afishamovies.presentation.ui.gallery.GalleryFragment
import dagger.BindsInstance
import dagger.Subcomponent

@Subcomponent(modules = [GalleryViewModelModule::class])
interface GalleryComponent {

    fun inject(fragment: GalleryFragment)

    @Subcomponent.Factory
    interface Factory {

        fun create(
            @BindsInstance @MovieIdQualifier movieId: Int
        ) : GalleryComponent
    }
}