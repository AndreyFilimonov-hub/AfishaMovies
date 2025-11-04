package com.filimonov.afishamovies.di

import com.filimonov.afishamovies.presentation.ui.searchpage.searchsettingsfragment.searchchoosefragment.FilterMode
import com.filimonov.afishamovies.presentation.ui.searchpage.searchsettingsfragment.searchchoosefragment.SearchChooseFragment
import dagger.BindsInstance
import dagger.Subcomponent

@Subcomponent(modules = [SearchChooseViewModelModule::class])
interface SearchChooseComponent {

    fun inject(fragment: SearchChooseFragment)

    @Subcomponent.Factory
    interface Factory {

        fun create(
            @BindsInstance @ModeQualifier filterMode: FilterMode,
            @BindsInstance @ChooseResIdQualifier chooseResId: Int) : SearchChooseComponent
    }
}