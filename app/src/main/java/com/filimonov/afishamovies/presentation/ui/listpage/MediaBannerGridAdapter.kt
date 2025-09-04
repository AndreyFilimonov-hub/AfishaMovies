package com.filimonov.afishamovies.presentation.ui.listpage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.filimonov.afishamovies.databinding.ItemImageBinding
import com.filimonov.afishamovies.databinding.ItemLoadingProgressBinding
import com.filimonov.afishamovies.domain.entities.MediaBannerEntity
import com.filimonov.afishamovies.presentation.model.MediaBannerUiModel
import com.filimonov.afishamovies.presentation.utils.MediaBannerViewHolder
import com.filimonov.afishamovies.presentation.utils.MediaDiffCallback

class MediaBannerGridAdapter(
    private val onMediaBannerClick: (MediaBannerEntity) -> Unit
) : ListAdapter<MediaBannerUiModel, RecyclerView.ViewHolder>(MediaDiffCallback()) {

    companion object {
        const val BANNER_TYPE = 0
        const val LOADING_TYPE = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            BANNER_TYPE -> MediaBannerViewHolder(
                ItemImageBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ),
                onMediaBannerClick
            )

            else -> LoadingViewHolder(
                ItemLoadingProgressBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is MediaBannerUiModel.Banner -> BANNER_TYPE
            else -> LOADING_TYPE
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        if (item is MediaBannerUiModel.Banner) {
            (holder as MediaBannerViewHolder).bind(item)
        }
    }

    fun getSpanPosition(position: Int): Int {
        return when (getItemViewType(position)) {
            LOADING_TYPE -> 2
            else -> 1
        }
    }

    class LoadingViewHolder(binding: ItemLoadingProgressBinding) :
        RecyclerView.ViewHolder(binding.root)
}