package com.filimonov.afishamovies.presentation.ui.filmpage.imagepreviewadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.filimonov.afishamovies.databinding.ItemImageBinding
import com.filimonov.afishamovies.domain.entities.ImagePreviewEntity

class ImagePreviewAdapter :
    ListAdapter<ImagePreviewEntity, ImagePreviewViewHolder>(ImagePreviewDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImagePreviewViewHolder {
        return ImagePreviewViewHolder(
            ItemImageBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            )
        )
    }

    override fun onBindViewHolder(holder: ImagePreviewViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}