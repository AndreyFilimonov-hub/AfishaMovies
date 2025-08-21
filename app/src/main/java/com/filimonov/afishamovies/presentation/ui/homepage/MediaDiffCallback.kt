package com.filimonov.afishamovies.presentation.ui.homepage

import androidx.recyclerview.widget.DiffUtil
import com.filimonov.afishamovies.domain.entities.MediaBannerEntity

class MediaDiffCallback : DiffUtil.ItemCallback<MediaBannerEntity>() {

    override fun areItemsTheSame(oldItem: MediaBannerEntity, newItem: MediaBannerEntity): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MediaBannerEntity, newItem: MediaBannerEntity): Boolean {
        return oldItem == newItem
    }
}