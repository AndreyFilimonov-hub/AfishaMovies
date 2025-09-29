package com.filimonov.afishamovies.di

import com.filimonov.afishamovies.presentation.ui.homepage.HomePageFragment
import dagger.Component

@ApplicationScope
@Component(modules = [DomainModule::class, NetworkModule::class, ViewModelModule::class])
interface AppComponent {

    fun listPageComponent(): ListPageComponent.Factory

    fun filmPageComponent(): FilmPageComponent.Factory

    fun galleryComponent(): GalleryComponent.Factory

    fun inject(fragment: HomePageFragment)
}