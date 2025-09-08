package com.filimonov.afishamovies.presentation.ui.listpage.mediabannergridadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.filimonov.afishamovies.databinding.ItemBannerBinding
import com.filimonov.afishamovies.databinding.ItemErrorLoadingBinding
import com.filimonov.afishamovies.databinding.ItemLoadingProgressBinding
import com.filimonov.afishamovies.domain.entities.MediaBannerEntity

class MediaBannerGridAdapter(
    private val onMediaBannerClick: (MediaBannerEntity) -> Unit,
    private val onRetryButtonClick: () -> Unit,
) : ListAdapter<ListPageMedia, RecyclerView.ViewHolder>(ListPageMediaDiffCallback()) {

    companion object {
        const val BANNER_TYPE = 0
        const val LOADING_TYPE = 1
        const val ERROR_TYPE = 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            BANNER_TYPE -> ListPageMediaBannerViewHolder(
                ItemBannerBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ),
                onMediaBannerClick
            )

            LOADING_TYPE -> ListPageLoadingViewHolder(
                ItemLoadingProgressBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

            else -> ListPageErrorViewHolder(
                ItemErrorLoadingBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ),
                onRetryButtonClick
            )
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is ListPageMedia.Banner -> BANNER_TYPE
            ListPageMedia.Loading -> LOADING_TYPE
            else -> ERROR_TYPE
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (val item = getItem(position)) {
            is ListPageMedia.Banner -> (holder as ListPageMediaBannerViewHolder).bind(item)
            is ListPageMedia.Error -> (holder as ListPageErrorViewHolder).bind()
            else -> {}
        }
    }

    fun getSpanPosition(position: Int): Int {
        return when (getItemViewType(position)) {
            LOADING_TYPE -> 2
            ERROR_TYPE -> 2
            else -> 1
        }
    }
}