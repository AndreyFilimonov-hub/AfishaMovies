package com.filimonov.afishamovies.presentation.ui.profilepage.mediabanneradapter

import androidx.recyclerview.widget.DiffUtil

class MediaBannerModelDiffCallback : DiffUtil.ItemCallback<MediaBannerModel>() {

    override fun areItemsTheSame(oldItem: MediaBannerModel, newItem: MediaBannerModel): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: MediaBannerModel, newItem: MediaBannerModel): Boolean {
        return oldItem == newItem
    }
}