package com.filimonov.afishamovies.presentation.utils

import androidx.recyclerview.widget.DiffUtil
import com.filimonov.afishamovies.presentation.model.MediaBannerUiModel

class MediaDiffCallback : DiffUtil.ItemCallback<MediaBannerUiModel>() {

    override fun areItemsTheSame(oldItem: MediaBannerUiModel, newItem: MediaBannerUiModel): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: MediaBannerUiModel, newItem: MediaBannerUiModel): Boolean {
        return oldItem == newItem
    }
}