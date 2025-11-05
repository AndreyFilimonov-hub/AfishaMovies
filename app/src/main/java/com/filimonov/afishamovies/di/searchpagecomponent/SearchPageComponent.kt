package com.filimonov.afishamovies.di.searchpagecomponent

import com.filimonov.afishamovies.di.searchpagecomponent.searchsettingscomponent.SearchSettingsComponent
import com.filimonov.afishamovies.di.searchpagecomponent.searchsettingscomponent.searchchoosecomponent.SearchChooseComponent
import com.filimonov.afishamovies.di.searchpagecomponent.searchsettingscomponent.searchchoosedatacomponent.SearchChooseDateComponent
import com.filimonov.afishamovies.presentation.ui.searchpage.SearchPageFragment
import dagger.Subcomponent

@Subcomponent(modules = [SearchPageViewModelModule::class])
interface SearchPageComponent {

    fun inject(fragment: SearchPageFragment)

    fun createSearchSettingsComponent(): SearchSettingsComponent.Factory

    fun createSearchChooseComponent(): SearchChooseComponent.Factory

    fun createSearchChooseDateComponent(): SearchChooseDateComponent.Factory

    @Subcomponent.Factory
    interface Factory {
        fun create() : SearchPageComponent
    }
}