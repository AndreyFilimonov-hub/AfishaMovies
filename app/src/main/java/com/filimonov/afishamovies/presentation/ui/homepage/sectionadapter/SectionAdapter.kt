package com.filimonov.afishamovies.presentation.ui.homepage.sectionadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.filimonov.afishamovies.R
import com.filimonov.afishamovies.databinding.ItemSectionBinding
import com.filimonov.afishamovies.domain.entities.MediaBannerEntity
import com.filimonov.afishamovies.presentation.ui.homepage.mediabannerhorizontaladapter.MediaBannerHorizontalAdapter
import com.filimonov.afishamovies.presentation.utils.HorizontalSpaceItemDecoration

class SectionAdapter(
    private val onShowAllClick: (MediaSection) -> Unit,
    private val onMediaClick: (MediaBannerEntity) -> Unit,
) :
    ListAdapter<MediaSection, SectionAdapter.SectionViewHolder>(MediaSectionDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SectionViewHolder {
        val binding = ItemSectionBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return SectionViewHolder(binding, onShowAllClick, onMediaClick)
    }

    override fun onBindViewHolder(holder: SectionViewHolder, position: Int) {
        val mediaSection = getItem(position)
        holder.bind(mediaSection)
    }

    class SectionViewHolder(
        private val binding: ItemSectionBinding,
        private val onShowAllClick: (MediaSection) -> Unit,
        private val onMediaClick: (MediaBannerEntity) -> Unit,
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(mediaSection: MediaSection) {
            binding.tvTitle.text = binding.root.context.resources.getText(mediaSection.title)

            val mediaBannerHorizontalAdapter =
                MediaBannerHorizontalAdapter(mediaSection, onShowAllClick, onMediaClick)
            binding.rvSection.adapter = mediaBannerHorizontalAdapter

            binding.rvSection.addItemDecoration(
                HorizontalSpaceItemDecoration(
                    binding.root.context.resources.getDimensionPixelSize(R.dimen.margin_start),
                    binding.root.context.resources.getDimensionPixelSize(R.dimen.space_between)
                )
            )

            mediaBannerHorizontalAdapter.submitList(mediaSection.mediaList)

            binding.tvAll.setOnClickListener {
                onShowAllClick(mediaSection)
            }
        }
    }
}