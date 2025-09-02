package com.filimonov.afishamovies.presentation.ui.listpage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ListPageViewModelProvider(private val categoryId: Int) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ListPageViewModel(categoryId) as T
    }
}