package com.filimonov.afishamovies.presentation.ui.gallery.imageadapter

import androidx.recyclerview.widget.DiffUtil
import com.filimonov.afishamovies.domain.entities.GalleryImageEntity

class ImageDiffCallback : DiffUtil.ItemCallback<GalleryModel>() {

    override fun areItemsTheSame(
        oldItem: GalleryModel,
        newItem: GalleryModel
    ): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(
        oldItem: GalleryModel,
        newItem: GalleryModel
    ): Boolean {
        return oldItem == newItem
    }
}