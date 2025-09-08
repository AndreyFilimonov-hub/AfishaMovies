package com.filimonov.afishamovies.presentation.ui.homepage.mediabannerhorizontaladapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.filimonov.afishamovies.databinding.ItemBannerBinding
import com.filimonov.afishamovies.databinding.ItemShowAllBinding
import com.filimonov.afishamovies.domain.entities.MediaBannerEntity
import com.filimonov.afishamovies.presentation.ui.homepage.sectionadapter.MediaSection

class MediaBannerHorizontalAdapter(
    private val mediaSection: MediaSection,
    private val onShowAllClick: (MediaSection) -> Unit,
    private val onMediaClick: (MediaBannerEntity) -> Unit,
) :
    ListAdapter<HomePageMedia, RecyclerView.ViewHolder>(HomePageMediaDiffCallback()) {

    companion object {

        private const val MEDIA_BANNER_TYPE = 0
        private const val SHOW_ALL_BUTTON_TYPE = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            MEDIA_BANNER_TYPE -> {
                HomePageMediaBannerViewHolder(
                    ItemBannerBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    ),
                    onMediaClick
                )
            }

            SHOW_ALL_BUTTON_TYPE -> {
                HomePageShowAllViewHolder(
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
            is HomePageMedia.Banner -> MEDIA_BANNER_TYPE
            else -> SHOW_ALL_BUTTON_TYPE
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = getItem(position)) {
            is HomePageMedia.Banner -> {
                (holder as HomePageMediaBannerViewHolder).bind(item)
            }

            else -> {
                (holder as HomePageShowAllViewHolder).bind()
            }
        }
    }
}