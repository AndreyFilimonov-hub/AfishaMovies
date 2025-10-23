package com.filimonov.afishamovies.di

import androidx.lifecycle.ViewModel
import com.filimonov.afishamovies.presentation.ui.searchpage.searchchoosedatafragment.SearchChooseDataViewModel
import com.filimonov.afishamovies.presentation.ui.searchpage.searchchoosefragment.SearchChooseViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface SearchChooseDateViewModelModule {

    @IntoMap
    @ViewModelKey(SearchChooseDataViewModel::class)
    @Binds
    fun bindListPageViewModel(viewModel: SearchChooseDataViewModel): ViewModel
}