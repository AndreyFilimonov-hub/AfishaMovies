package com.filimonov.afishamovies.presentation.adapters

import androidx.recyclerview.widget.DiffUtil
import com.filimonov.afishamovies.domain.entities.Movie

class MovieDiffCallback : DiffUtil.ItemCallback<Movie>() {

    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem == newItem
    }
}