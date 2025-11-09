package com.filimonov.afishamovies.di.searchpagecomponent

import androidx.lifecycle.ViewModel
import com.filimonov.afishamovies.di.ViewModelKey
import com.filimonov.afishamovies.presentation.ui.searchpage.SearchPageViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface SearchPageViewModelModule {

    @IntoMap
    @ViewModelKey(SearchPageViewModel::class)
    @Binds
    fun bindSearchPageViewModel(viewModel: SearchPageViewModel): ViewModel
}