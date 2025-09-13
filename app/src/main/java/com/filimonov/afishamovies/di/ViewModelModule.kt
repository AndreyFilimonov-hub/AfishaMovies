package com.filimonov.afishamovies.di

import androidx.lifecycle.ViewModel
import com.filimonov.afishamovies.presentation.ui.homepage.HomePageViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @IntoMap
    @ViewModelKey(HomePageViewModel::class)
    @Binds
    fun bindHomePageViewModel(viewModel: HomePageViewModel): ViewModel
}