package com.filimonov.afishamovies.presentation.ui.filmpage.imagepreviewadapter

import androidx.recyclerview.widget.DiffUtil
import com.filimonov.afishamovies.domain.entities.ImagePreviewEntity

class ImagePreviewDiffCallback : DiffUtil.ItemCallback<ImagePreviewEntity>() {

    override fun areItemsTheSame(
        oldItem: ImagePreviewEntity,
        newItem: ImagePreviewEntity
    ): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(
        oldItem: ImagePreviewEntity,
        newItem: ImagePreviewEntity
    ): Boolean {
        return oldItem == newItem
    }
}