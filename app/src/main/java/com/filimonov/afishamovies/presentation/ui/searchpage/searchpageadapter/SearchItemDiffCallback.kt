package com.filimonov.afishamovies.presentation.ui.searchpage.searchpageadapter

import androidx.recyclerview.widget.DiffUtil
import com.filimonov.afishamovies.presentation.ui.searchpage.SearchItem

class SearchItemDiffCallback : DiffUtil.ItemCallback<SearchItem>() {

    override fun areItemsTheSame(oldItem: SearchItem, newItem: SearchItem): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: SearchItem, newItem: SearchItem): Boolean {
        return oldItem == newItem
    }
}