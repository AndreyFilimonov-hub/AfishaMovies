package com.filimonov.afishamovies.presentation.ui.searchpage.searchchoosefragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.graphics.toColorInt
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.filimonov.afishamovies.databinding.ChooseFilterItemBinding

class SearchChooseAdapter(
    private val launchItem: String?,
    private val onItemClick: (String) -> Unit
) : ListAdapter<String, SearchChooseAdapter.SearchChooseViewHolder>(
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
        holder.bind(getItem(position))
    }

    class SearchChooseViewHolder(
        private val binding: ChooseFilterItemBinding,
        private val launchItem: String?,
        private val onItemClick: (String) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: String) {
            launchItem?.let {
                if (item == launchItem) {
                    binding.root.setBackgroundColor("#22000000".toColorInt())
                }
            }
            binding.tvFilterItem.text = item
            binding.root.setOnClickListener {
                onItemClick(item)
            }
        }
    }
}