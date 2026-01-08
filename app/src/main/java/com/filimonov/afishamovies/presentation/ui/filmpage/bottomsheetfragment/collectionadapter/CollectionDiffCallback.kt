package com.filimonov.afishamovies.presentation.ui.filmpage.bottomsheetfragment.collectionadapter

import androidx.recyclerview.widget.DiffUtil
import com.filimonov.afishamovies.domain.entities.CollectionWithMovieEntity

class CollectionDiffCallback : DiffUtil.ItemCallback<CollectionWithMovieEntity>() {

    override fun areItemsTheSame(
        oldItem: CollectionWithMovieEntity,
        newItem: CollectionWithMovieEntity
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: CollectionWithMovieEntity,
        newItem: CollectionWithMovieEntity
    ): Boolean {
        return oldItem == newItem
    }
}