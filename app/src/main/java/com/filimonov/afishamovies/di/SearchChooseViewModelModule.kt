package com.filimonov.afishamovies.di

import androidx.lifecycle.ViewModel
import com.filimonov.afishamovies.presentation.ui.searchpage.searchsettingsfragment.searchchoosefragment.SearchChooseViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface SearchChooseViewModelModule {

    @IntoMap
    @ViewModelKey(SearchChooseViewModel::class)
    @Binds
    fun bindListPageViewModel(viewModel: SearchChooseViewModel): ViewModel
}