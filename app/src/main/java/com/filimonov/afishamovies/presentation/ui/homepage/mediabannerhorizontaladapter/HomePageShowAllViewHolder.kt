package com.filimonov.afishamovies.presentation.ui.homepage.mediabannerhorizontaladapter

import androidx.recyclerview.widget.RecyclerView
import com.filimonov.afishamovies.databinding.ItemShowAllBinding
import com.filimonov.afishamovies.presentation.ui.homepage.sectionadapter.MediaSection

class HomePageShowAllViewHolder(
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