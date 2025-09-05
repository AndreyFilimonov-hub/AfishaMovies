package com.filimonov.afishamovies.presentation.ui.homepage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.filimonov.afishamovies.databinding.ItemBannerBinding
import com.filimonov.afishamovies.databinding.ItemShowAllBinding
import com.filimonov.afishamovies.domain.entities.MediaBannerEntity
import com.filimonov.afishamovies.presentation.model.MediaBannerUiModel
import com.filimonov.afishamovies.presentation.utils.MediaBannerViewHolder
import com.filimonov.afishamovies.presentation.utils.MediaDiffCallback

class MediaBannerHorizontalAdapter(
    private val mediaSection: MediaSection,
    private val onShowAllClick: (MediaSection) -> Unit,
    private val onMediaClick: (MediaBannerEntity) -> Unit,
) :
    ListAdapter<MediaBannerUiModel, RecyclerView.ViewHolder>(MediaDiffCallback()) {

    companion object {

        private const val MEDIA_BANNER_TYPE = 0
        private const val SHOW_ALL_BUTTON_TYPE = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            MEDIA_BANNER_TYPE -> {
                MediaBannerViewHolder(
                    ItemBannerBinding.inflate(
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
            is MediaBannerUiModel.Banner -> MEDIA_BANNER_TYPE
            else -> SHOW_ALL_BUTTON_TYPE
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = getItem(position)) {
            is MediaBannerUiModel.Banner -> {
                (holder as MediaBannerViewHolder).bind(item)
            }

            else -> {
                (holder as ShowAllViewHolder).bind()
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