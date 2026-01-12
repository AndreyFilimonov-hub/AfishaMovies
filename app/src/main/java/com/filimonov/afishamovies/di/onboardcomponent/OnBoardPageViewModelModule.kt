package com.filimonov.afishamovies.di.onboardcomponent

import androidx.lifecycle.ViewModel
import com.filimonov.afishamovies.di.ViewModelKey
import com.filimonov.afishamovies.presentation.ui.onboardpage.OnBoardPageViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface OnBoardPageViewModelModule {

    @IntoMap
    @ViewModelKey(OnBoardPageViewModel::class)
    @Binds
    fun bindOnBoardPageViewModel(viewModel: OnBoardPageViewModel): ViewModel
}