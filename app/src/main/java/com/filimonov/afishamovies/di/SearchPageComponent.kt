package com.filimonov.afishamovies.di

import com.filimonov.afishamovies.presentation.ui.searchpage.SearchPageFragment
import dagger.Subcomponent

@Subcomponent(modules = [SearchPageViewModelModule::class])
interface SearchPageComponent {

    fun inject(fragment: SearchPageFragment)

    fun createSearchChooseComponent(): SearchChooseComponent.Factory

    @Subcomponent.Factory
    interface Factory {
        fun create() : SearchPageComponent
    }
}