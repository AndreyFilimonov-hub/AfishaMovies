package com.filimonov.afishamovies.presentation.ui.homepage.mediabannerhorizontaladapter

import androidx.recyclerview.widget.DiffUtil

class HomePageMediaDiffCallback : DiffUtil.ItemCallback<HomePageMedia>() {

    override fun areItemsTheSame(oldItem: HomePageMedia, newItem: HomePageMedia): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: HomePageMedia, newItem: HomePageMedia): Boolean {
        return oldItem == newItem
    }
}