package com.filimonov.afishamovies.presentation.ui.filmpage.similarmovieadapter

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.filimonov.afishamovies.R
import com.filimonov.afishamovies.databinding.ItemBannerBinding
import com.filimonov.afishamovies.domain.entities.MediaBannerEntity

class MovieBannerViewHolder(
    private val binding: ItemBannerBinding,
    private val onMediaBannerClick: (MediaBannerEntity) -> Unit
) :
    RecyclerView.ViewHolder(binding.root) {

    companion object {

        private const val RADIUS_PX = 4
    }

    fun bind(mediaBanner: MediaBannerEntity) {
        binding.tvName.text = mediaBanner.name
        Glide.with(binding.root)
            .load(mediaBanner.posterUrl)
            .transform(
                CenterCrop(),
                RoundedCorners((RADIUS_PX * binding.root.context.resources.displayMetrics.density).toInt())
            )
            .placeholder(R.drawable.onboard_second)
            .into(binding.ivPoster)
        binding.tvGenre.text = mediaBanner.genreMain
        binding.tvRating.text = mediaBanner.rating.toString().substring(0, 3)

        binding.bannerContainer.setOnClickListener {
            onMediaBannerClick(mediaBanner)
        }
    }
}