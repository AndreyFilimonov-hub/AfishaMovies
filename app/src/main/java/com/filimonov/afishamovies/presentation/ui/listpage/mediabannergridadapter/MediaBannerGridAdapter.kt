package com.filimonov.afishamovies.presentation.ui.listpage.mediabannergridadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.filimonov.afishamovies.databinding.ItemBannerBinding
import com.filimonov.afishamovies.databinding.ItemBannerGridBinding
import com.filimonov.afishamovies.databinding.ItemErrorLoadingBinding
import com.filimonov.afishamovies.databinding.ItemLoadingProgressBinding
import com.filimonov.afishamovies.domain.entities.MediaBannerEntity
import com.filimonov.afishamovies.domain.entities.PersonBannerEntity

class MediaBannerGridAdapter(
    private val onMediaBannerClick: (MediaBannerEntity) -> Unit,
    private val onPersonBannerClick: (PersonBannerEntity) -> Unit,
    private val onRetryButtonClick: () -> Unit,
) : ListAdapter<ListPageMedia, RecyclerView.ViewHolder>(ListPageMediaDiffCallback()) {

    companion object {
        const val MEDIA_BANNER_TYPE = 0
        const val ACTOR_BANNER_TYPE = 1
        const val WORKER_BANNER_TYPE = 2
        const val LOADING_TYPE = 3
        const val ERROR_TYPE = 4
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            MEDIA_BANNER_TYPE -> ListPageMediaBannerViewHolder(
                ItemBannerGridBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ),
                onMediaBannerClick
            )

            ACTOR_BANNER_TYPE -> ListPageActorBannerViewHolder(
                ItemBannerGridBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ),
                onPersonBannerClick
            )

            WORKER_BANNER_TYPE -> ListPageWorkerBannerViewHolder(
                ItemBannerGridBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ),
                onPersonBannerClick
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
            is ListPageMedia.MediaBanner -> MEDIA_BANNER_TYPE
            is ListPageMedia.ActorBanner -> ACTOR_BANNER_TYPE
            is ListPageMedia.WorkerBanner -> WORKER_BANNER_TYPE
            ListPageMedia.Loading -> LOADING_TYPE
            else -> ERROR_TYPE
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (val item = getItem(position)) {
            is ListPageMedia.MediaBanner -> (holder as ListPageMediaBannerViewHolder).bind(item)
            is ListPageMedia.ActorBanner -> (holder as ListPageActorBannerViewHolder).bind(item)
            is ListPageMedia.WorkerBanner -> (holder as ListPageWorkerBannerViewHolder).bind(item)
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