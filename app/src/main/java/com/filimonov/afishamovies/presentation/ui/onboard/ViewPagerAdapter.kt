package com.filimonov.afishamovies.presentation.ui.onboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.filimonov.afishamovies.databinding.ItemPageBinding

class ViewPagerAdapter(private val onBoardModels: List<OnBoardModel>) : RecyclerView.Adapter<ViewPagerAdapter.PagerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder {
        val binding = ItemPageBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PagerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        val item = onBoardModels[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return onBoardModels.size
    }

    class PagerViewHolder(private val binding: ItemPageBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: OnBoardModel) {
            binding.ivLoaded.setImageResource(item.resId)
            binding.tvTitle.text = item.title
        }
    }
}