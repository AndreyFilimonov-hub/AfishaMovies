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
import com.filimonov.afishamovies.databinding.ItemShowAllBinding
import com.filimonov.afishamovies.domain.entities.MediaBannerEntity

class MediaHorizontalAdapter(
    private val mediaSection: MediaSection,
    private val onShowAllClick: (MediaSection) -> Unit,
    private val onMediaClick: (MediaBannerEntity) -> Unit,
) :
    ListAdapter<Media, RecyclerView.ViewHolder>(MediaDiffCallback()) {

    companion object {

        private const val MEDIA_BANNER_TYPE = 0
        private const val SHOW_ALL_BUTTON_TYPE = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            MEDIA_BANNER_TYPE -> {
                MediaViewHolder(
                    ItemImageBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    ),
                    onMediaClick
                )
            }

            SHOW_ALL_BUTTON_TYPE -> {
                ShowAllViewHolder(
                    ItemShowAllBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    ),
                    mediaSection,
                    onShowAllClick
                )
            }

            else -> {
                throw IllegalArgumentException("Unknown type")
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is Media.MediaBanner -> MEDIA_BANNER_TYPE
            is Media.ShowAll -> SHOW_ALL_BUTTON_TYPE
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = getItem(position)) {
            is Media.MediaBanner -> {
                (holder as MediaViewHolder).bind(item)
            }

            Media.ShowAll -> {
                (holder as ShowAllViewHolder).bind()
            }
        }
    }

    class MediaViewHolder(
        private val binding: ItemImageBinding,
        private val onMediaClick: (MediaBannerEntity) -> Unit
        ) :
        RecyclerView.ViewHolder(binding.root) {

        companion object {

            private const val RADIUS_PX = 4
        }

        fun bind(mediaBanner: Media.MediaBanner) {
            binding.tvName.text = mediaBanner.media.name
            Glide.with(binding.root)
                .load(mediaBanner.media.posterUrl)
                .transform(
                    CenterCrop(),
                    RoundedCorners((RADIUS_PX * binding.root.context.resources.displayMetrics.density).toInt())
                )
                .placeholder(R.drawable.onboard_second)
                .into(binding.ivPoster)
            binding.tvGenre.text = mediaBanner.media.name
            binding.tvRating.text = mediaBanner.media.rating.toString().substring(0, 3)

            binding.ivPoster.setOnClickListener {
                onMediaClick(mediaBanner.media)
            }
        }
    }

    class ShowAllViewHolder(
        private val binding: ItemShowAllBinding,
        private val mediaSection: MediaSection,
        private val onShowAllClick: (MediaSection) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind() {
            binding.ibShowAll.setOnClickListener {
                onShowAllClick(mediaSection)
            }
        }
    }
}