package com.filimonov.afishamovies.presentation.ui.gallery.imageadapter

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.filimonov.afishamovies.databinding.ItemImageGalleryBigBinding
import com.filimonov.afishamovies.domain.entities.GalleryImageEntity

class BigImageViewHolder(private val binding: ItemImageGalleryBigBinding) :
    RecyclerView.ViewHolder(binding.root) {

    companion object {

        private const val RADIUS_PX = 4
    }

    fun bind(photo: GalleryImageEntity) {
        Glide.with(binding.ivGalleryBigImage)
            .load(photo.url)
            .transform(
                CenterCrop(),
                RoundedCorners((RADIUS_PX * binding.root.context.resources.displayMetrics.density).toInt())
            )
            .into(binding.ivGalleryBigImage)
    }
}