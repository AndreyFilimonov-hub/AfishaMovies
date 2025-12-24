package com.filimonov.afishamovies.di.profilepagecomponent

import androidx.lifecycle.ViewModel
import com.filimonov.afishamovies.di.ViewModelKey
import com.filimonov.afishamovies.presentation.ui.profilepage.ProfilePageViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ProfilePageViewModelModule {

    @IntoMap
    @ViewModelKey(ProfilePageViewModel::class)
    @Binds
    fun bindProfilePageViewModel(viewModel: ProfilePageViewModel): ViewModel
}