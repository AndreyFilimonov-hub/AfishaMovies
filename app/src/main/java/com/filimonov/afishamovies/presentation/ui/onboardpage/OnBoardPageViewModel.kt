package com.filimonov.afishamovies.presentation.ui.onboardpage

import androidx.lifecycle.ViewModel
import com.filimonov.afishamovies.R
import javax.inject.Inject

class OnBoardPageViewModel @Inject constructor() : ViewModel() {

    fun getOnBoardModels() = listOf(
        OnBoardModel("Узнавай \nо премьерах", R.drawable.onboard_first),
        OnBoardModel("Создавай \nколлекции", R.drawable.onboard_second),
        OnBoardModel("Делись \nс друзьями", R.drawable.onboard_third),
    )
}