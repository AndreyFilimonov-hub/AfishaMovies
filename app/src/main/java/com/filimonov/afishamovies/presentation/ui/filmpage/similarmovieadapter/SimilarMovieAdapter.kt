package com.filimonov.afishamovies.presentation.ui.filmpage.similarmovieadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.filimonov.afishamovies.databinding.ItemBannerBinding
import com.filimonov.afishamovies.domain.entities.MediaBannerEntity

class SimilarMovieAdapter(
    private val onClick: (MediaBannerEntity) -> Unit,
) :
    ListAdapter<MediaBannerEntity, MovieBannerViewHolder>(MovieBannerDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieBannerViewHolder {
        return MovieBannerViewHolder(
            ItemBannerBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            onClick
        )
    }

    override fun onBindViewHolder(holder: MovieBannerViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}