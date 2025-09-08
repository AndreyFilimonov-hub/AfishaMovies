package com.filimonov.afishamovies.presentation.ui.listpage.mediabannergridadapter

import androidx.recyclerview.widget.DiffUtil

class ListPageMediaDiffCallback : DiffUtil.ItemCallback<ListPageMedia>() {

    override fun areItemsTheSame(oldItem: ListPageMedia, newItem: ListPageMedia): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: ListPageMedia, newItem: ListPageMedia): Boolean {
        return oldItem == newItem
    }
}