package com.filimonov.afishamovies.di.searchpagecomponent.searchsettingscomponent

import com.filimonov.afishamovies.presentation.ui.searchpage.ShowType
import com.filimonov.afishamovies.presentation.ui.searchpage.SortType
import com.filimonov.afishamovies.presentation.ui.searchpage.searchsettingsfragment.SearchSettingsFragment
import dagger.BindsInstance
import dagger.Subcomponent

@Subcomponent(modules = [SearchSettingsViewModelModule::class])
interface SearchSettingsComponent {

    fun inject(fragment: SearchSettingsFragment)

    @Subcomponent.Factory
    interface Factory {
        fun create(
            @BindsInstance @ShowTypeQualifier showType: ShowType,
            @BindsInstance @SortTypeQualifier sortType: SortType,
            @BindsInstance @CountryQualifier countryResId: String?,
            @BindsInstance @GenreQualifier genreResId: String?,
            @BindsInstance @YearFromQualifier yearFrom: Int,
            @BindsInstance @YearToQualifier yearTo: Int,
            @BindsInstance @RatingFromQualifier ratingFrom: Float,
            @BindsInstance @RatingToQualifier ratingTo: Float,
            @BindsInstance @IsDontWatchedQualifier isDontWatched: Boolean
        ): SearchSettingsComponent
    }
}