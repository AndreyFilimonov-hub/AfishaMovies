package com.filimonov.afishamovies.presentation.ui.profilepage.mediabanneradapter

import androidx.recyclerview.widget.RecyclerView
import com.filimonov.afishamovies.databinding.ItemClearHistoryBinding

class ClearHistoryViewHolder(
    private val binding: ItemClearHistoryBinding,
    private val onClick: () -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind() {
        binding.ibClearHistory.setOnClickListener {
            onClick
        }
    }
}