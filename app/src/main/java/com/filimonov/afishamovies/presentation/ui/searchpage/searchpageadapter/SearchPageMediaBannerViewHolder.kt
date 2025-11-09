package com.filimonov.afishamovies.presentation.ui.searchpage.searchpageadapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.filimonov.afishamovies.R
import com.filimonov.afishamovies.databinding.ItemBannerHorizontalBinding
import com.filimonov.afishamovies.domain.entities.SearchMediaBannerEntity
import com.filimonov.afishamovies.presentation.ui.searchpage.SearchItem

class SearchPageMediaBannerViewHolder(
    private val binding: ItemBannerHorizontalBinding,
    private val onMediaBannerClick: (SearchMediaBannerEntity) -> Unit
) :
    RecyclerView.ViewHolder(binding.root) {

    companion object {

        private const val RADIUS_PX = 4
    }

    fun bind(mediaBanner: SearchItem.MediaBanner) {
        binding.tvName.text = mediaBanner.mediaBanner.name
        Glide.with(binding.root)
            .load(mediaBanner.mediaBanner.posterUrl)
            .transform(
                CenterCrop(),
                RoundedCorners((RADIUS_PX * binding.root.context.resources.displayMetrics.density).toInt())
            )
            .placeholder(R.drawable.onboard_second)
            .into(binding.ivPoster)

        if (mediaBanner.mediaBanner.rating == null) {
            binding.tvRating.visibility = View.GONE
        } else {
            binding.tvRating.text = mediaBanner.mediaBanner.rating
        }

        binding.tvGenre.text = mediaBanner.mediaBanner.genres?.firstOrNull()

        binding.bannerContainer.setOnClickListener {
            onMediaBannerClick(mediaBanner.mediaBanner)
        }
    }
}