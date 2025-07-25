package com.filimonov.afishamovies.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.filimonov.afishamovies.R
import com.filimonov.afishamovies.databinding.FragmentHomePageBinding
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
            binding.tvGenre.text = movie.genres
            Glide.with(binding.cvItemImage)
                .load(movie.poster)
                .placeholder(R.drawable.onboard_second)
                .into(binding.ivPoster)
            binding.tvRating.text = movie.rating.toString().substring(0, 3)

        }
    }
}