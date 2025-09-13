package com.filimonov.afishamovies.di

import com.filimonov.afishamovies.presentation.ui.listpage.ListPageFragment
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
            @BindsInstance @CategoryIdQualifier categoryId: Int
        ): ListPageComponent
    }
}