package com.filimonov.afishamovies.presentation.ui.homepage.mediabannerhorizontaladapter

import androidx.recyclerview.widget.DiffUtil

class MediaDiffCallback : DiffUtil.ItemCallback<HomePageMediaBanner>() {

    override fun areItemsTheSame(oldItem: HomePageMediaBanner, newItem: HomePageMediaBanner): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: HomePageMediaBanner, newItem: HomePageMediaBanner): Boolean {
        return oldItem == newItem
    }
}