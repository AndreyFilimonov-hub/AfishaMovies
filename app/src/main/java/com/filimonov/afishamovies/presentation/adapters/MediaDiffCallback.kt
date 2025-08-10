package com.filimonov.afishamovies.presentation.adapters

import androidx.recyclerview.widget.DiffUtil
import com.filimonov.afishamovies.domain.entities.Media

class MediaDiffCallback : DiffUtil.ItemCallback<Media>() {

    override fun areItemsTheSame(oldItem: Media, newItem: Media): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Media, newItem: Media): Boolean {
        return oldItem == newItem
    }
}