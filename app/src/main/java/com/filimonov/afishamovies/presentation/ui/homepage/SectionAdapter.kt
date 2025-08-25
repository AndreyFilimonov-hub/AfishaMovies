package com.filimonov.afishamovies.presentation.ui.homepage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.filimonov.afishamovies.R
import com.filimonov.afishamovies.databinding.ItemImageBinding
import com.filimonov.afishamovies.databinding.ItemSectionBinding
import com.filimonov.afishamovies.domain.entities.MediaBannerEntity

class SectionAdapter :
    ListAdapter<MediaSection, SectionAdapter.MediaViewHolder>(MediaSectionDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MediaViewHolder {
        val binding = ItemSectionBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MediaViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MediaViewHolder, position: Int) {
        val mediaSection = getItem(position)
        holder.bind(mediaSection)
    }

    class MediaViewHolder(private val binding: ItemSectionBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(mediaSection: MediaSection) {
            binding.tvTitle.text = mediaSection.title

            val mediaHorizontalAdapter = MediaHorizontalAdapter()
            binding.rvComedyRussian.adapter = mediaHorizontalAdapter

            mediaHorizontalAdapter.submitList(mediaSection.mediaList)
        }
    }
}