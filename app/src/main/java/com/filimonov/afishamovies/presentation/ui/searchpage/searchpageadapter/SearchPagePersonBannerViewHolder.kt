package com.filimonov.afishamovies.presentation.ui.searchpage.searchpageadapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.filimonov.afishamovies.R
import com.filimonov.afishamovies.databinding.ItemBannerHorizontalBinding
import com.filimonov.afishamovies.domain.entities.PersonBannerEntity
import com.filimonov.afishamovies.presentation.ui.searchpage.SearchItem

class SearchPagePersonBannerViewHolder(
    private val binding: ItemBannerHorizontalBinding,
    private val onPersonBannerClick: (PersonBannerEntity) -> Unit
) :
    RecyclerView.ViewHolder(binding.root) {

    companion object {

        private const val RADIUS_PX = 4
    }

    fun bind(actorBanner: SearchItem.PersonBanner) {
        binding.tvName.text = actorBanner.personBanner.name
        Glide.with(binding.root)
            .load(actorBanner.personBanner.photo)
            .transform(
                CenterCrop(),
                RoundedCorners((RADIUS_PX * binding.root.context.resources.displayMetrics.density).toInt())
            )
            .placeholder(R.drawable.onboard_second)
            .into(binding.ivPoster)

        binding.tvRating.visibility = View.GONE

        binding.bannerContainer.setOnClickListener {
            onPersonBannerClick(actorBanner.personBanner)
        }
    }
}