package com.filimonov.afishamovies.presentation.ui.searchpage.searchchoosefragment

import androidx.recyclerview.widget.DiffUtil

class SearchChooseDiffCallback : DiffUtil.ItemCallback<String>() {

    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }
}