package com.filimonov.afishamovies.di.homepagecomponent

import com.filimonov.afishamovies.presentation.ui.homepage.HomePageFragment
import dagger.Subcomponent

@Subcomponent(modules = [HomePageViewModelModule::class])
interface HomePageComponent {

    fun inject(fragment: HomePageFragment)

    @Subcomponent.Factory
    interface Factory {

        fun create(): HomePageComponent
    }
}