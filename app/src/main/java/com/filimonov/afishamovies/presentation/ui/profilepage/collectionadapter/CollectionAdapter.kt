package com.filimonov.afishamovies.presentation.ui.profilepage.collectionadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.filimonov.afishamovies.databinding.ItemCollectionBinding
import com.filimonov.afishamovies.domain.entities.CollectionEntity

class CollectionAdapter(
    private val onCollectionClickListener: () -> Unit,
    private val onDeleteCollectionClick: (Int) -> Unit
) : ListAdapter<CollectionEntity, CollectionViewHolder>(CollectionDiffCallback()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CollectionViewHolder {
        return CollectionViewHolder(
            ItemCollectionBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            onCollectionClickListener,
            onDeleteCollectionClick
        )
    }

    override fun onBindViewHolder(
        holder: CollectionViewHolder,
        position: Int
    ) {
        val item = getItem(position)
        holder.bind(item)
    }
}