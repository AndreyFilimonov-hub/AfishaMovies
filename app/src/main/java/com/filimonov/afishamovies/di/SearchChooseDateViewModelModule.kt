package com.filimonov.afishamovies.di

import androidx.lifecycle.ViewModel
import com.filimonov.afishamovies.presentation.ui.searchpage.searchsettingsfragment.searchchoosedatafragment.SearchChooseDataViewModel
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