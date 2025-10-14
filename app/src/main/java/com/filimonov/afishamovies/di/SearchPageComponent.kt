package com.filimonov.afishamovies.di

import com.filimonov.afishamovies.presentation.ui.searchpage.SearchPageFragment
import dagger.Subcomponent

@Subcomponent(modules = [SearchPageViewModelModule::class])
interface SearchPageComponent {

    fun inject(fragment: SearchPageFragment)

    @Subcomponent.Factory
    interface Factory {
        fun create() : SearchPageComponent
    }
}