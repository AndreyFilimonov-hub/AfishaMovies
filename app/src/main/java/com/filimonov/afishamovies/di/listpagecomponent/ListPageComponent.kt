package com.filimonov.afishamovies.di.listpagecomponent

import com.filimonov.afishamovies.di.ModeQualifier
import com.filimonov.afishamovies.presentation.ui.listpage.ListPageFragment
import com.filimonov.afishamovies.presentation.ui.listpage.ListPageMode
import dagger.BindsInstance
import dagger.Subcomponent

@Subcomponent(
    modules = [ListPageViewModelModule::class]
)
interface ListPageComponent {

    fun inject(fragment: ListPageFragment)

    @Subcomponent.Factory
    interface Factory {

        fun create(
            @BindsInstance @IdQualifier id: Int,
            @BindsInstance @ModeQualifier mode: ListPageMode
        ): ListPageComponent
    }
}