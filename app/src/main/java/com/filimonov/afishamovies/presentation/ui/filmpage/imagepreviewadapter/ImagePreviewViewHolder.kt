package com.filimonov.afishamovies.presentation.ui.filmpage.imagepreviewadapter

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.filimonov.afishamovies.databinding.ItemImageSmallBinding
import com.filimonov.afishamovies.domain.entities.ImagePreviewEntity

class ImagePreviewViewHolder(
    private val binding: ItemImageSmallBinding
) :
    RecyclerView.ViewHolder(binding.root) {

    companion object {

        private const val RADIUS_PX = 4
    }

    fun bind(image: ImagePreviewEntity) {
        Glide.with(binding.root)
            .load(image.url)
            .transform(
                CenterCrop(),
                RoundedCorners((RADIUS_PX * binding.root.context.resources.displayMetrics.density).toInt())
            )
            .into(binding.root)
    }
}