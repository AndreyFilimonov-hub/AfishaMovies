package com.filimonov.afishamovies.presentation.ui.filmpage.personadapter

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.filimonov.afishamovies.databinding.ItemPersonBinding
import com.filimonov.afishamovies.domain.entities.PersonBannerEntity
import com.filimonov.afishamovies.presentation.utils.cutWordEnd

class PersonViewHolder(
    private val binding: ItemPersonBinding
) :
    RecyclerView.ViewHolder(binding.root) {

    companion object {

        private const val RADIUS_PX = 4
    }

    fun bind(person: PersonBannerEntity) {

        binding.tvName.text = person.name
        if (person.profession == "актеры") {
            binding.tvCharacter.text = person.character
        } else {
            binding.tvCharacter.text = person.profession.cutWordEnd()
        }
        Glide.with(binding.ivAvatar)
            .load(person.photo)
            .transform(
                CenterCrop(),
                RoundedCorners((RADIUS_PX * binding.root.context.resources.displayMetrics.density).toInt())
            )
            .into(binding.ivAvatar)
    }
}