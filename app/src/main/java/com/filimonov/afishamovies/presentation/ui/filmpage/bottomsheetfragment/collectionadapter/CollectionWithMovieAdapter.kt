package com.filimonov.afishamovies.presentation.ui.filmpage.bottomsheetfragment.collectionadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.filimonov.afishamovies.databinding.ItemAddToCollectionBinding
import com.filimonov.afishamovies.domain.entities.CollectionWithMovieEntity

class CollectionWithMovieAdapter(
    private val onCollectionClickListener: (CollectionWithMovieEntity, Boolean) -> Unit
) : ListAdapter<CollectionWithMovieEntity, AddCollectionViewHolder>(CollectionDiffCallback()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AddCollectionViewHolder {
        return AddCollectionViewHolder(
            ItemAddToCollectionBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            onCollectionClickListener
        )
    }

    override fun onBindViewHolder(
        holder: AddCollectionViewHolder,
        position: Int
    ) {
        val item = getItem(position)
        holder.bind(item)
    }
}