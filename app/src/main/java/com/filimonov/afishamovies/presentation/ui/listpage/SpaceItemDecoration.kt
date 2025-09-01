package com.filimonov.afishamovies.presentation.ui.listpage

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration

class SpaceItemDecoration(
    private val marginStart: Int,
    private val marginEnd: Int,
    private val marginBetween: Int,
    private val marginBottom: Int
) : ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)

        when {
            position % 2 == 0 -> {
                outRect.set(marginStart, 0, marginBetween, marginBottom)
            }
            else -> {
                outRect.set(marginBetween, 0, marginEnd, marginBottom)
            }
        }
    }
}