package com.filimonov.afishamovies.presentation.ui.homepage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.filimonov.afishamovies.R
import com.filimonov.afishamovies.databinding.ItemImageBinding
import com.filimonov.afishamovies.domain.entities.MediaBannerEntity

class MediaHorizontalAdapter :
    ListAdapter<MediaBannerEntity, MediaHorizontalAdapter.MediaViewHolder>(MediaDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MediaViewHolder {
        val binding = ItemImageBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MediaViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MediaViewHolder, position: Int) {
        val movie = getItem(position)
        holder.bind(movie)
    }

    class MediaViewHolder(private val binding: ItemImageBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(mediaBannerEntity: MediaBannerEntity) {
            binding.tvName.text = mediaBannerEntity.name
            Glide.with(binding.root)
                .load(mediaBannerEntity.posterUrl)
                .transform(
                    CenterCrop(),
                    RoundedCorners((RADIUS_PX * binding.root.context.resources.displayMetrics.density).toInt())
                )
                .placeholder(R.drawable.onboard_second)
                .into(binding.ivPoster)
            binding.tvGenre.text = mediaBannerEntity.genreMain
            binding.tvRating.text = mediaBannerEntity.rating.toString().substring(0, 3)
        }

        companion object {

            private const val RADIUS_PX = 4
        }
    }
}