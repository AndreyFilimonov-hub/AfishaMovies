package com.filimonov.afishamovies.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(modules = [DomainModule::class, NetworkModule::class])
interface AppComponent {

    fun homePageComponent(): HomePageComponent.Factory

    fun listPageComponent(): ListPageComponent.Factory

    fun filmPageComponent(): FilmPageComponent.Factory

    fun galleryComponent(): GalleryComponent.Factory

    fun searchPageComponent(): SearchPageComponent.Factory

    @Component.Factory
    interface AppComponentFactory {

        fun create(@BindsInstance context: Context): AppComponent

    }
}