package com.filimonov.afishamovies.presentation.adapters

import androidx.recyclerview.widget.DiffUtil
import com.filimonov.afishamovies.domain.entities.Movie
import com.filimonov.afishamovies.domain.entities.Series

class SeriesDiffCallback : DiffUtil.ItemCallback<Series>() {

    override fun areItemsTheSame(oldItem: Series, newItem: Series): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Series, newItem: Series): Boolean {
        return oldItem == newItem
    }
}