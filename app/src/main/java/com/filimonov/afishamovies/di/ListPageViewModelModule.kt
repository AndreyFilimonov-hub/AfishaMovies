package com.filimonov.afishamovies.di

import androidx.lifecycle.ViewModel
import com.filimonov.afishamovies.presentation.ui.listpage.ListPageViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ListPageViewModelModule {

    @IntoMap
    @ViewModelKey(ListPageViewModel::class)
    @Binds
    fun bindListPageViewModel(viewModel: ListPageViewModel): ViewModel
}