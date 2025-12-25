package com.filimonov.afishamovies.presentation.ui.profilepage.collectionadapter

import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.filimonov.afishamovies.R
import com.filimonov.afishamovies.databinding.ItemCollectionBinding
import com.filimonov.afishamovies.domain.entities.CollectionEntity

class CollectionViewHolder(
    private val binding: ItemCollectionBinding,
    private val onCollectionClickListener: () -> Unit,
    private val onDeleteCollectionClick: (Int) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(collection: CollectionEntity) {
        val context = binding.root.context
        val icon = when (collection.name) {
            context.getString(R.string.liked) -> ContextCompat.getDrawable(context,R.drawable.like_black)
            context.getString(R.string.want_to_watch) -> ContextCompat.getDrawable(context, R.drawable.favourite_black)
            else -> ContextCompat.getDrawable(context, R.drawable.person_black)
        }
        binding.ivIcon.setImageDrawable(icon)
        binding.tvName.text = collection.name
        binding.tvCount.text = collection.countElements.toString()

        binding.ivDeleteCollection.visibility = if (collection.isDefault) {
            View.INVISIBLE
        } else {
            View.VISIBLE
        }

        if (!collection.isDefault) {
            binding.ivDeleteCollection.setOnClickListener {
                onDeleteCollectionClick(collection.id)
            }
        }

        binding.root.setOnClickListener {
            onCollectionClickListener
        }
    }
}