package com.filimonov.afishamovies.presentation.ui.gallery.imageadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.filimonov.afishamovies.databinding.ItemErrorLoadingBinding
import com.filimonov.afishamovies.databinding.ItemImageGalleryBigBinding
import com.filimonov.afishamovies.databinding.ItemImageGallerySmallBinding
import com.filimonov.afishamovies.databinding.ItemLoadingProgressBinding

class ImageAdapter(
    private val onRetryClick: () -> Unit
) : ListAdapter<GalleryModel, RecyclerView.ViewHolder>(ImageDiffCallback()) {

    companion object {
        const val BIG_IMAGE_TYPE = 0
        const val SMALL_IMAGE_TYPE = 1
        const val ERROR_TYPE = 2
        const val LOADING_TYPE = 3
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            BIG_IMAGE_TYPE -> BigImageViewHolder(
                ItemImageGalleryBigBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

            SMALL_IMAGE_TYPE -> SmallImageViewHolder(
                ItemImageGallerySmallBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

            LOADING_TYPE -> GalleryLoadingViewHolder(
                ItemLoadingProgressBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

            else -> GalleryErrorViewHolder(
                ItemErrorLoadingBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ),
                onRetryClick
            )
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            GalleryModel.Error -> ERROR_TYPE
            is GalleryModel.Image -> {
                if ((position + 1) % 3 == 0) {
                    BIG_IMAGE_TYPE
                } else {
                    SMALL_IMAGE_TYPE
                }
            }

            GalleryModel.Loading -> LOADING_TYPE
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(val item = getItem(position)) {
            GalleryModel.Error -> (holder as GalleryErrorViewHolder).bind()
            is GalleryModel.Image -> {
                if ((position + 1) % 3 == 0) {
                    (holder as BigImageViewHolder).bind(item.image)
                } else {
                    (holder as SmallImageViewHolder).bind(item.image)
                }
            }
            else -> {}
        }
    }

    fun getSpanPosition(position: Int): Int {
        return when (getItemViewType(position)) {
            BIG_IMAGE_TYPE -> 2
            ERROR_TYPE -> 2
            LOADING_TYPE -> 2
            else -> 1
        }
    }
}