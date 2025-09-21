package com.filimonov.afishamovies.presentation.ui.listpage.mediabannergridadapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.filimonov.afishamovies.R
import com.filimonov.afishamovies.databinding.ItemBannerBinding
import com.filimonov.afishamovies.domain.entities.PersonBannerEntity
import com.filimonov.afishamovies.presentation.utils.cutWordEnd

class ListPageWorkerBannerViewHolder(
    private val binding: ItemBannerBinding,
    private val onPersonBannerClick: (PersonBannerEntity) -> Unit
) :
    RecyclerView.ViewHolder(binding.root) {

    companion object {

        private const val RADIUS_PX = 4
    }

    fun bind(workerBanner: ListPageMedia.WorkerBanner) {
        binding.tvName.text = workerBanner.worker.name
        Glide.with(binding.root)
            .load(workerBanner.worker.photo)
            .transform(
                CenterCrop(),
                RoundedCorners((RADIUS_PX * binding.root.context.resources.displayMetrics.density).toInt())
            )
            .placeholder(R.drawable.onboard_second)
            .into(binding.ivPoster)

        binding.tvRating.visibility = View.GONE

        binding.tvGenre.text = workerBanner.worker.profession.cutWordEnd()

        binding.bannerContainer.setOnClickListener {
            onPersonBannerClick(workerBanner.worker)
        }
    }
}