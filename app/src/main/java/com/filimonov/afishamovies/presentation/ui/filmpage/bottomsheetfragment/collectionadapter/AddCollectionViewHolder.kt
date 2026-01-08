package com.filimonov.afishamovies.presentation.ui.filmpage.bottomsheetfragment.collectionadapter

import androidx.recyclerview.widget.RecyclerView
import com.filimonov.afishamovies.databinding.ItemAddToCollectionBinding
import com.filimonov.afishamovies.domain.entities.CollectionWithMovieEntity

class AddCollectionViewHolder(
    private val binding: ItemAddToCollectionBinding,
    private val onCollectionClick: (CollectionWithMovieEntity, Boolean) -> Unit
) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(collection: CollectionWithMovieEntity) {
        binding.tvName.text = collection.name
        binding.tvCount.text = collection.countElements.toString()
        binding.cbSelect.isChecked = collection.isMovieInCollection
        binding.clCollection.setOnClickListener {
            binding.cbSelect.isChecked = !binding.cbSelect.isChecked
            onCollectionClick(collection, binding.cbSelect.isChecked)
        }
    }
}