package com.filimonov.afishamovies.presentation.ui.filmpage.personadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.filimonov.afishamovies.databinding.ItemPersonBinding
import com.filimonov.afishamovies.domain.entities.PersonBannerEntity

class PersonAdapter(private val onClick: (Int) -> Unit) :
    ListAdapter<PersonBannerEntity, PersonViewHolder>(PersonBannerDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder {
        return PersonViewHolder(
            ItemPersonBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            )
        )
    }

    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.itemView.setOnClickListener { onClick(position) }
    }
}