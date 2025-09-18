package com.filimonov.afishamovies.presentation.ui.filmpage.personadapter

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration

class WorkersItemDecoration(
    private val spaceStartEnd: Int,
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

        when {
            position < 2 -> outRect.set(spaceStartEnd, spaceTopBottom, spaceBetween, spaceTopBottom)
            position > 7 -> outRect.set(
                spaceBetween,
                spaceTopBottom,
                spaceStartEnd,
                spaceTopBottom
            )

            else -> outRect.set(spaceBetween, spaceTopBottom, spaceBetween, spaceTopBottom)
        }
    }
}