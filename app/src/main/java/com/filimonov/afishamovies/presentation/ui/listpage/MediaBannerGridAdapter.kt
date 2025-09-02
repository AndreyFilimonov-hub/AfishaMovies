package com.filimonov.afishamovies.presentation.ui.listpage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.filimonov.afishamovies.databinding.ItemImageBinding
import com.filimonov.afishamovies.domain.entities.MediaBannerEntity
import com.filimonov.afishamovies.presentation.model.MediaBannerUiModel
import com.filimonov.afishamovies.presentation.utils.MediaBannerViewHolder
import com.filimonov.afishamovies.presentation.utils.MediaDiffCallback

class MediaBannerGridAdapter(
    private val onMediaBannerClick: (MediaBannerEntity) -> Unit
) : ListAdapter<MediaBannerUiModel, MediaBannerViewHolder>(MediaDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MediaBannerViewHolder {
        return MediaBannerViewHolder(
            ItemImageBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            onMediaBannerClick
        )
    }

    override fun onBindViewHolder(holder: MediaBannerViewHolder, position: Int) {
        val item = getItem(position)
        if (item is MediaBannerUiModel.Banner) {
            holder.bind(item)
        }
    }
}