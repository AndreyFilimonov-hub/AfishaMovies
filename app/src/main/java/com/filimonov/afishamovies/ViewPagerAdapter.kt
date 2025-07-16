package com.filimonov.afishamovies

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.filimonov.afishamovies.databinding.ItemPageBinding

class ViewPagerAdapter : RecyclerView.Adapter<ViewPagerAdapter.PagerViewHolder>() {

    private val onBoards = listOf<OnBoard>(
        OnBoard("Узнавай \nо премьерах" ,R.drawable.onboard_first),
        OnBoard("Создавай \nколлекции", R.drawable.onboard_second),
        OnBoard("Делись \nс друзьями", R.drawable.onboard_third),
    )


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder {
        val binding = ItemPageBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PagerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        val item = onBoards[position]
        holder.binding.ivLoaded.setImageResource(item.resId)
        holder.binding.tvTitle.text = item.text
    }

    override fun getItemCount(): Int {
        return onBoards.size
    }

    class PagerViewHolder(val binding: ItemPageBinding) : RecyclerView.ViewHolder(binding.root)
}