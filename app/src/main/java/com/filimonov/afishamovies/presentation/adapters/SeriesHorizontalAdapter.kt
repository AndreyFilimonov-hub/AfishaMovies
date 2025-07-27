package com.filimonov.afishamovies.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.filimonov.afishamovies.R
import com.filimonov.afishamovies.databinding.ItemImageBinding
import com.filimonov.afishamovies.domain.entities.Series

class SeriesHorizontalAdapter :
    ListAdapter<Series, SeriesHorizontalAdapter.MoviesViewHolder>(SeriesDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        val binding = ItemImageBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MoviesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        val series = getItem(position)
        holder.bind(series)
    }

    class MoviesViewHolder(private val binding: ItemImageBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(series: Series) {
            binding.tvName.text = series.name
            Glide.with(binding.root)
                .load(series.poster)
                .transform(CenterCrop() ,RoundedCorners((4 * binding.root.context.resources.displayMetrics.density).toInt()))
                .placeholder(R.drawable.onboard_second)
                .into(binding.ivPoster)
            binding.tvGenre.text = series.genreMain
            binding.tvRating.text = series.rating.toString().substring(0, 3)

        }
    }
}