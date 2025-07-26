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
import com.filimonov.afishamovies.domain.entities.Movie

class MoviesHorizontalAdapter :
    ListAdapter<Movie, MoviesHorizontalAdapter.MoviesViewHolder>(MovieDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        val binding = ItemImageBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MoviesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        val movie = getItem(position)
        holder.bind(movie)
    }

    class MoviesViewHolder(private val binding: ItemImageBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Movie) {
            binding.tvName.text = movie.name
            Glide.with(binding.root)
                .load(movie.poster)
                .transform(CenterCrop() ,RoundedCorners((4 * binding.root.context.resources.displayMetrics.density).toInt()))
                .placeholder(R.drawable.onboard_second)
                .into(binding.ivPoster)
            binding.tvGenre.text = movie.genreMain
            binding.tvRating.text = movie.rating.toString().substring(0, 3)

        }
    }
}