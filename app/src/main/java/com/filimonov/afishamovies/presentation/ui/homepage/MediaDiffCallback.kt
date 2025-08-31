package com.filimonov.afishamovies.presentation.ui.homepage

import androidx.recyclerview.widget.DiffUtil

class MediaDiffCallback : DiffUtil.ItemCallback<Media>() {

    override fun areItemsTheSame(oldItem: Media, newItem: Media): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Media, newItem: Media): Boolean {
        return oldItem == newItem
    }
}