package com.filimonov.afishamovies.di.searchpagecomponent.searchsettingscomponent

import androidx.lifecycle.ViewModel
import com.filimonov.afishamovies.di.ViewModelKey
import com.filimonov.afishamovies.presentation.ui.searchpage.searchsettingsfragment.SearchSettingsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface SearchSettingsViewModelModule {

    @IntoMap
    @ViewModelKey(SearchSettingsViewModel::class)
    @Binds
    fun bindSearchSettingsViewModel(viewModel: SearchSettingsViewModel): ViewModel
}