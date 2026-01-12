package com.filimonov.afishamovies.presentation.ui.listpage.mediabannergridadapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.filimonov.afishamovies.R
import com.filimonov.afishamovies.databinding.ItemBannerBinding
import com.filimonov.afishamovies.databinding.ItemBannerGridBinding
import com.filimonov.afishamovies.domain.entities.PersonBannerEntity

class ListPageActorBannerViewHolder(
    private val binding: ItemBannerGridBinding,
    private val onPersonBannerClick: (PersonBannerEntity) -> Unit
) :
    RecyclerView.ViewHolder(binding.root) {

    companion object {

        private const val RADIUS_PX = 4
    }

    fun bind(actorBanner: ListPageMedia.ActorBanner) {
        binding.tvName.text = actorBanner.actor.name
        Glide.with(binding.root)
            .load(actorBanner.actor.photo)
            .transform(
                CenterCrop(),
                RoundedCorners((RADIUS_PX * binding.root.context.resources.displayMetrics.density).toInt())
            )
            .placeholder(R.drawable.onboard_second)
            .into(binding.ivPoster)

        binding.tvRating.visibility = View.GONE

        binding.tvGenre.text = actorBanner.actor.character

        binding.bannerContainer.setOnClickListener {
            onPersonBannerClick(actorBanner.actor)
        }
    }
}