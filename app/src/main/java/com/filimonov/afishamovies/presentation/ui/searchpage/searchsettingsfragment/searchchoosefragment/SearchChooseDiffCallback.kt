package com.filimonov.afishamovies.presentation.ui.searchpage.searchsettingsfragment.searchchoosefragment

import androidx.recyclerview.widget.DiffUtil

class SearchChooseDiffCallback : DiffUtil.ItemCallback<ChooseItem>() {

    override fun areItemsTheSame(oldItem: ChooseItem, newItem: ChooseItem): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: ChooseItem, newItem: ChooseItem): Boolean {
        return oldItem.itemResId == newItem.itemResId
    }
}