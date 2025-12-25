package com.filimonov.afishamovies.presentation.ui.profilepage.mediabanneradapter

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.filimonov.afishamovies.R
import com.filimonov.afishamovies.databinding.ItemBannerBinding
import com.filimonov.afishamovies.domain.entities.MediaBannerEntity

class MediaBannerViewHolder(
    private val binding: ItemBannerBinding,
    private val onMediaBannerClick: (MediaBannerEntity) -> Unit
) :
    RecyclerView.ViewHolder(binding.root) {

    companion object {

        private const val RADIUS_PX = 4
    }

    fun bind(mediaBanner: MediaBannerModel.Banner) {
        binding.tvName.text = mediaBanner.banner.name
        Glide.with(binding.root)
            .load(mediaBanner.banner.posterUrl)
            .transform(
                CenterCrop(),
                RoundedCorners((RADIUS_PX * binding.root.context.resources.displayMetrics.density).toInt())
            )
            .placeholder(R.drawable.onboard_second)
            .into(binding.ivPoster)
        binding.tvGenre.text = mediaBanner.banner.genreMain
        binding.tvRating.text = mediaBanner.banner.rating

        binding.bannerContainer.setOnClickListener {
            onMediaBannerClick(mediaBanner.banner)
        }
    }
}