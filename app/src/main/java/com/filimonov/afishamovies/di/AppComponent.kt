package com.filimonov.afishamovies.di

import dagger.Component

@ApplicationScope
@Component(modules = [DomainModule::class, NetworkModule::class])
interface AppComponent {

    fun homePageComponent(): HomePageComponent.Factory

    fun listPageComponent(): ListPageComponent.Factory

    fun filmPageComponent(): FilmPageComponent.Factory

    fun galleryComponent(): GalleryComponent.Factory

    fun searchPageComponent(): SearchPageComponent.Factory
}