package com.filimonov.afishamovies.presentation.ui.gallery.imageadapter

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration

class ImageSpaceDecoration(
    private val adapter: ImageAdapter,
    private val spaceStart: Int,
    private val spaceEnd: Int,
    private val spaceBetween: Int,
    private val spaceTopBottom: Int
) :
    ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
        if (position == RecyclerView.NO_POSITION) return
        val viewType = adapter.getItemViewType(position)

        if (viewType == ImageAdapter.LOADING_TYPE || viewType == ImageAdapter.ERROR_TYPE) {
            outRect.set(0, 0, 0, 0)
            return
        }

        val number = parent.getChildAdapterPosition(view) + 1

        when (number % 3) {
            0 -> outRect.set(spaceStart, spaceTopBottom, spaceEnd, spaceTopBottom)
            1 -> outRect.set(spaceStart, spaceTopBottom, spaceBetween, spaceTopBottom)
            2 -> outRect.set(spaceBetween, spaceTopBottom, spaceEnd, spaceTopBottom)
        }
    }
}