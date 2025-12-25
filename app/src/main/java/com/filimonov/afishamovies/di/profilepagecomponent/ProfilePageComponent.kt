package com.filimonov.afishamovies.di.profilepagecomponent

import com.filimonov.afishamovies.presentation.ui.profilepage.ProfilePageFragment
import dagger.Subcomponent

@Subcomponent(modules = [ProfilePageViewModelModule::class])
interface ProfilePageComponent {

    fun inject(fragment: ProfilePageFragment)

    @Subcomponent.Factory
    interface Factory {
        fun create(): ProfilePageComponent
    }
}