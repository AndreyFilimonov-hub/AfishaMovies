package com.filimonov.afishamovies.presentation.ui.searchpage.searchsettingsfragment.searchchoosefragment

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.filimonov.afishamovies.R
import com.filimonov.afishamovies.databinding.ChooseFilterItemBinding

class SearchChooseAdapter(
    private val launchItem: Int,
    private val onItemClick: (Int) -> Unit
) : ListAdapter<ChooseItem, SearchChooseAdapter.SearchChooseViewHolder>(
    SearchChooseDiffCallback()
) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SearchChooseViewHolder {
        return SearchChooseViewHolder(
            ChooseFilterItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            launchItem,
            onItemClick
        )
    }

    override fun onBindViewHolder(
        holder: SearchChooseViewHolder,
        position: Int
    ) {
        holder.bind(getItem(position).itemResId)
    }

    class SearchChooseViewHolder(
        private val binding: ChooseFilterItemBinding,
        private val launchItem: Int?,
        private val onItemClick: (Int) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(itemResId: Int) {
            if (itemResId == launchItem) {
                binding.root.setBackgroundColor(
                    ContextCompat.getColor(
                        itemView.context,
                        R.color.selected_item
                    )
                )
            } else {
                binding.root.setBackgroundColor(Color.TRANSPARENT)
            }

            binding.tvFilterItem.text = itemView.context.getString(itemResId)
            binding.root.setOnClickListener {
                onItemClick(itemResId)
            }
        }
    }
}