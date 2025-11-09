package com.filimonov.afishamovies.presentation.ui.searchpage.searchpageadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.filimonov.afishamovies.databinding.ItemBannerHorizontalBinding
import com.filimonov.afishamovies.databinding.ItemErrorLoadingBinding
import com.filimonov.afishamovies.databinding.ItemLoadingProgressBinding
import com.filimonov.afishamovies.domain.entities.SearchMediaBannerEntity
import com.filimonov.afishamovies.presentation.ui.listpage.mediabannergridadapter.ListPageErrorViewHolder
import com.filimonov.afishamovies.presentation.ui.listpage.mediabannergridadapter.ListPageLoadingViewHolder
import com.filimonov.afishamovies.presentation.ui.searchpage.SearchItem

class SearchItemAdapter(
    private val onMediaBannerClick: (SearchMediaBannerEntity) -> Unit,
    private val onRetryButtonClick: () -> Unit,
) : ListAdapter<SearchItem, RecyclerView.ViewHolder>(SearchItemDiffCallback()) {

    companion object {
        const val MEDIA_BANNER_TYPE = 0
        const val LOADING_TYPE = 1
        const val ERROR_TYPE = 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            MEDIA_BANNER_TYPE -> SearchPageMediaBannerViewHolder(
                ItemBannerHorizontalBinding.inflate(
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
            is SearchItem.MediaBanner -> MEDIA_BANNER_TYPE
            SearchItem.Loading -> LOADING_TYPE
            else -> ERROR_TYPE
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (val item = getItem(position)) {
            is SearchItem.MediaBanner -> (holder as SearchPageMediaBannerViewHolder).bind(item)
            is SearchItem.Error -> (holder as ListPageErrorViewHolder).bind()
            else -> {}
        }
    }
}