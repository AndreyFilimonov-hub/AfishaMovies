package com.filimonov.afishamovies.di

import android.app.Application
import com.filimonov.afishamovies.di.filmpagecomponent.FilmPageComponent
import com.filimonov.afishamovies.di.gallerypagecomponent.GalleryComponent
import com.filimonov.afishamovies.di.homepagecomponent.HomePageComponent
import com.filimonov.afishamovies.di.listpagecomponent.ListPageComponent
import com.filimonov.afishamovies.di.onboardcomponent.OnBoardPageComponent
import com.filimonov.afishamovies.di.profilepagecomponent.ProfilePageComponent
import com.filimonov.afishamovies.di.searchpagecomponent.SearchPageComponent
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(modules = [DomainModule::class, NetworkModule::class, DataModule::class])
interface AppComponent {

    fun homePageComponent(): HomePageComponent.Factory

    fun listPageComponent(): ListPageComponent.Factory

    fun filmPageComponent(): FilmPageComponent.Factory

    fun galleryComponent(): GalleryComponent.Factory

    fun searchPageComponent(): SearchPageComponent.Factory

    fun profilePageComponent(): ProfilePageComponent.Factory

    fun onBoardPageComponent(): OnBoardPageComponent.Factory

    @Component.Factory
    interface AppComponentFactory {

        fun create(@BindsInstance application: Application): AppComponent

    }
}