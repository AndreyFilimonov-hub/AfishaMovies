package com.filimonov.afishamovies.presentation.ui.homepage.sectionadapter

import androidx.recyclerview.widget.DiffUtil

class MediaSectionDiffCallback : DiffUtil.ItemCallback<MediaSection>() {

    override fun areItemsTheSame(oldItem: MediaSection, newItem: MediaSection): Boolean {
        return oldItem.title == newItem.title
    }

    override fun areContentsTheSame(oldItem: MediaSection, newItem: MediaSection): Boolean {
        return oldItem == newItem
    }
}