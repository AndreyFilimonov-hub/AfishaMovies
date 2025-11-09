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
    private val launchItem: String,
    private val onItemClick: (String) -> Unit
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
        private val launchItem: String,
        private val onItemClick: (String) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(itemResId: Int) {
            val item = itemView.context.getString(itemResId)
            if (item == launchItem) {
                binding.root.setBackgroundColor(
                    ContextCompat.getColor(
                        itemView.context,
                        R.color.selected_item
                    )
                )
            } else {
                binding.root.setBackgroundColor(Color.TRANSPARENT)
            }

            binding.tvFilterItem.text = item
            binding.root.setOnClickListener {
                onItemClick(item)
            }
        }
    }
}