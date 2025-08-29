package com.filimonov.afishamovies.presentation.ui.homepage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.filimonov.afishamovies.R
import com.filimonov.afishamovies.databinding.ItemSectionBinding
import com.filimonov.afishamovies.domain.entities.MediaBannerEntity
import com.filimonov.afishamovies.presentation.ui.HorizontalSpaceItemDecoration

class SectionAdapter(
    private val onShowAllClick: (String) -> Unit,
    private val onMediaClick: (MediaBannerEntity) -> Unit,
) :
    ListAdapter<MediaSection, SectionAdapter.MediaViewHolder>(MediaSectionDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MediaViewHolder {
        val binding = ItemSectionBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MediaViewHolder(binding, onShowAllClick, onMediaClick)
    }

    override fun onBindViewHolder(holder: MediaViewHolder, position: Int) {
        val mediaSection = getItem(position)
        holder.bind(mediaSection)
    }

    class MediaViewHolder(
        private val binding: ItemSectionBinding,
        private val onShowAllClick: (String) -> Unit,
        private val onMediaClick: (MediaBannerEntity) -> Unit,
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(mediaSection: MediaSection) {
            binding.tvTitle.text = mediaSection.title

            val mediaHorizontalAdapter =
                MediaHorizontalAdapter(mediaSection.title, onShowAllClick, onMediaClick)
            binding.rvSection.adapter = mediaHorizontalAdapter

            binding.rvSection.addItemDecoration(
                HorizontalSpaceItemDecoration(
                    binding.root.context.resources.getDimensionPixelSize(R.dimen.margin_start),
                    binding.root.context.resources.getDimensionPixelSize(R.dimen.space_between)
                )
            )

            mediaHorizontalAdapter.submitList(
                mediaSection.mediaList.map { Media.MediaBanner(it) } + Media.ShowAll
            )

            binding.tvAll.setOnClickListener {
                onShowAllClick(binding.tvTitle.text.toString())
            }
        }
    }
}