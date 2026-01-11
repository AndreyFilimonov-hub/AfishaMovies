package com.filimonov.afishamovies.presentation.ui.profilepage.collectionadapter

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration

class CollectionItemDecoration(context: Context) : ItemDecoration() {

    private val spacing26 = (26 * context.resources.displayMetrics.density).toInt()

    private val spacing16 = (16 * context.resources.displayMetrics.density).toInt()

    private val spacing8 = (8 * context.resources.displayMetrics.density).toInt()

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
        if (position == RecyclerView.NO_POSITION) return

        val column = position % 2

        if (column == 0) {
            outRect.left = spacing26
            outRect.right = spacing8
        } else {
            outRect.right = spacing26
            outRect.left = spacing8
        }

        outRect.bottom = spacing16

        if (position < 2) {
            outRect.top = spacing16
        }
    }
}