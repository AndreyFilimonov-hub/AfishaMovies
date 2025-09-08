package com.filimonov.afishamovies.presentation.ui.listpage.mediabannergridadapter

import androidx.recyclerview.widget.RecyclerView
import com.filimonov.afishamovies.databinding.ItemErrorLoadingBinding

class ListPageErrorViewHolder(
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