package com.filimonov.afishamovies.presentation.ui.filmpage.similarmovieadapter

import androidx.recyclerview.widget.DiffUtil
import com.filimonov.afishamovies.domain.entities.MediaBannerEntity

class MovieBannerDiffCallback : DiffUtil.ItemCallback<MediaBannerEntity>() {

    override fun areItemsTheSame(oldItem: MediaBannerEntity, newItem: MediaBannerEntity): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(
        oldItem: MediaBannerEntity,
        newItem: MediaBannerEntity
    ): Boolean {
        return oldItem == newItem
    }
}