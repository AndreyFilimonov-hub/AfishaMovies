package com.filimonov.afishamovies.presentation.ui.profilepage.mediabanneradapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.filimonov.afishamovies.databinding.ItemBannerBinding
import com.filimonov.afishamovies.databinding.ItemClearHistoryBinding
import com.filimonov.afishamovies.domain.entities.MediaBannerEntity

class MediaBannerAdapter(
    private val onMediaBannerClick: (MediaBannerEntity) -> Unit,
    private val onClearHistoryClick: () -> Unit
) : ListAdapter<MediaBannerModel, RecyclerView.ViewHolder>(MediaBannerModelDiffCallback()) {

    companion object {

        private const val MEDIA_BANNER = 1
        private const val CLEAR_BUTTON = 2
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return when (viewType) {
            MEDIA_BANNER -> {
                MediaBannerViewHolder(
                    ItemBannerBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    ),
                    onMediaBannerClick
                )
            }

            else -> {
                ClearHistoryViewHolder(
                    ItemClearHistoryBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    ),
                    onClearHistoryClick
                )
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is MediaBannerModel.Banner -> MEDIA_BANNER
            MediaBannerModel.ClearHistory -> CLEAR_BUTTON
        }
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        when(val item = getItem(position)) {
            is MediaBannerModel.Banner -> (holder as MediaBannerViewHolder).bind(item)
            MediaBannerModel.ClearHistory -> (holder as ClearHistoryViewHolder).bind()
        }
    }
}