package com.filimonov.afishamovies.presentation.ui.listpage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.filimonov.afishamovies.databinding.ItemBannerBinding
import com.filimonov.afishamovies.databinding.ItemErrorLoadingBinding
import com.filimonov.afishamovies.databinding.ItemLoadingProgressBinding
import com.filimonov.afishamovies.domain.entities.MediaBannerEntity
import com.filimonov.afishamovies.presentation.model.MediaBannerUiModel
import com.filimonov.afishamovies.presentation.ui.homepage.mediabannerhorizontaladapter.MediaBannerViewHolder
import com.filimonov.afishamovies.presentation.ui.homepage.mediabannerhorizontaladapter.MediaDiffCallback

class MediaBannerGridAdapter(
    private val onMediaBannerClick: (MediaBannerEntity) -> Unit,
    private val onRetryButtonClick: () -> Unit,
) : ListAdapter<MediaBannerUiModel, RecyclerView.ViewHolder>(MediaDiffCallback()) {

    companion object {
        const val BANNER_TYPE = 0
        const val LOADING_TYPE = 1
        const val ERROR_TYPE = 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            BANNER_TYPE -> MediaBannerViewHolder(
                ItemBannerBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ),
                onMediaBannerClick
            )

            LOADING_TYPE -> LoadingViewHolder(
                ItemLoadingProgressBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

            else -> ErrorViewHolder(
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
            is MediaBannerUiModel.Banner -> BANNER_TYPE
            MediaBannerUiModel.Loading -> LOADING_TYPE
            else -> ERROR_TYPE
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (val item = getItem(position)) {
            is MediaBannerUiModel.Banner -> (holder as MediaBannerViewHolder).bind(item)
            is MediaBannerUiModel.Error -> (holder as ErrorViewHolder).bind()
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

    class LoadingViewHolder(binding: ItemLoadingProgressBinding) :
        RecyclerView.ViewHolder(binding.root)

    class ErrorViewHolder(
        private val binding: ItemErrorLoadingBinding,
        private val onRetryButtonClick: () -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind() {
            binding.buttonReload.setOnClickListener {
                onRetryButtonClick()
            }
        }
    }
}