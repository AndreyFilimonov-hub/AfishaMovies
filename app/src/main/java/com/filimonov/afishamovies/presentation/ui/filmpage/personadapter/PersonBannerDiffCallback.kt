package com.filimonov.afishamovies.presentation.ui.filmpage.personadapter

import androidx.recyclerview.widget.DiffUtil
import com.filimonov.afishamovies.domain.entities.PersonBannerEntity

class PersonBannerDiffCallback : DiffUtil.ItemCallback<PersonBannerEntity>() {

    override fun areItemsTheSame(
        oldItem: PersonBannerEntity,
        newItem: PersonBannerEntity
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: PersonBannerEntity,
        newItem: PersonBannerEntity
    ): Boolean {
        return oldItem == newItem
    }
}