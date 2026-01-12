package com.filimonov.afishamovies.di.onboardcomponent

import com.filimonov.afishamovies.presentation.ui.onboardpage.OnBoardPageFragment
import dagger.Subcomponent

@Subcomponent(modules = [OnBoardPageViewModelModule::class])
interface OnBoardPageComponent {

    fun inject(fragment: OnBoardPageFragment)

    @Subcomponent.Factory
    interface Factory {

        fun create(): OnBoardPageComponent
    }
}