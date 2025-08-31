package com.filimonov.afishamovies.presentation.ui.homepage

import androidx.recyclerview.widget.DiffUtil
import com.filimonov.afishamovies.domain.entities.MediaBannerEntity

class MediaSectionDiffCallback : DiffUtil.ItemCallback<MediaSection>() {

    override fun areItemsTheSame(oldItem: MediaSection, newItem: MediaSection): Boolean {
        return oldItem.title == newItem.title
    }

    override fun areContentsTheSame(oldItem: MediaSection, newItem: MediaSection): Boolean {
        return oldItem == newItem
    }
}