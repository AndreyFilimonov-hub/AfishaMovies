package com.filimonov.afishamovies.presentation.ui.gallery.imageadapter

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.filimonov.afishamovies.databinding.ItemImageGallerySmallBinding
import com.filimonov.afishamovies.domain.entities.GalleryImageEntity

class SmallImageViewHolder(private val binding: ItemImageGallerySmallBinding) :
    RecyclerView.ViewHolder(binding.root) {

    companion object {

        private const val RADIUS_PX = 4
    }

    fun bind(photo: GalleryImageEntity) {
        Glide.with(binding.root)
            .load(photo.url)
            .transform(
                CenterCrop(),
                RoundedCorners((RADIUS_PX * binding.root.context.resources.displayMetrics.density).toInt())
            )
            .into(binding.root)
    }
}