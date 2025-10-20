package com.filimonov.afishamovies.presentation.ui.searchpage.searchchoosedatafragment

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.filimonov.afishamovies.databinding.ItemYearBinding

class YearsAdapter(
    private var minActiveYear: Int = Int.MIN_VALUE,
    private var maxActiveYear: Int = Int.MAX_VALUE,
    private var selectedYear: Int,
    private val onClick: (Int?) -> Unit
) : ListAdapter<Int, YearsAdapter.YearViewHolder>(SearchChooseDateDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): YearViewHolder {
        val binding = ItemYearBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return YearViewHolder(binding)
    }

    fun updateSelectedYear(year: Int?) {
        val oldYear = selectedYear
        selectedYear = year ?: UNDEFINED_YEAR

        val oldIndex = currentList.indexOf(oldYear)
        val newIndex = currentList.indexOf(year)

        if (oldYear != -1) notifyItemChanged(oldIndex)
        if (newIndex != -1) notifyItemChanged(newIndex)
    }

    fun setMinActiveYear(year: Int?) {
        minActiveYear = year ?: Int.MIN_VALUE
        notifyItemRangeChanged(0, currentList.size)
    }

    fun setMaxActiveYear(year: Int?) {
        maxActiveYear = year ?: Int.MAX_VALUE
        notifyItemRangeChanged(0, currentList.size)
    }

    override fun onBindViewHolder(holder: YearViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {

        private const val UNDEFINED_YEAR = -1
    }

    inner class YearViewHolder(private val binding: ItemYearBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(year: Int) {
            with(binding.tvYear) {
                text = year.toString()

                val isActive = year >= minActiveYear && year <= maxActiveYear
                isEnabled = isActive
                alpha = if (isActive) 1f else 0.4f

                setTextColor(if (selectedYear == year) Color.BLUE else Color.BLACK)
                setOnClickListener {
                    if (year == selectedYear) {
                        onClick(null)
                    } else {
                        onClick(year)
                    }
                }
            }
        }
    }
}
