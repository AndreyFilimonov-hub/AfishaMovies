package com.filimonov.afishamovies.di

import com.filimonov.afishamovies.presentation.ui.searchpage.searchsettingsfragment.searchchoosedatafragment.SearchChooseDataFragment
import dagger.BindsInstance
import dagger.Subcomponent

@Subcomponent(modules = [SearchChooseDateViewModelModule::class])
interface SearchChooseDateComponent {

    fun inject(fragment: SearchChooseDataFragment)

    @Subcomponent.Factory
    interface Factory {

        fun create(
            @BindsInstance @SelectedYearFromQualifier selectedYearFrom: Int?,
            @BindsInstance @SelectedYearToQualifier selectedYearTo: Int?,
        ): SearchChooseDateComponent
    }
}