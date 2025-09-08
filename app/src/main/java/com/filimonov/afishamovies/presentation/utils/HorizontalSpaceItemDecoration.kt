package com.filimonov.afishamovies.presentation.utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration

class HorizontalSpaceItemDecoration(private val spaceStartEnd: Int, private val spaceBetween: Int):
    ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
        val itemCount = state.itemCount

        when(position) {
            0 -> outRect.set(spaceStartEnd, 0, spaceBetween, 0)
            itemCount - 1 -> outRect.set(spaceBetween, 0, spaceStartEnd, 0)
            else -> outRect.set(spaceBetween, 0, spaceBetween, 0)
        }
    }
}