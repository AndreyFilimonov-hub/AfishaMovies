package com.filimonov.afishamovies.presentation.ui.profilepage.collectionadapter

import android.graphics.Rect
import android.view.View
import androidx.core.view.size
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration

class CollectionItemDecoration : ItemDecoration() {

    companion object {
        private const val DP_26 = 26
        private const val DP_16 = 16
        private const val DP_8 = 8
    }

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
            outRect.right = DP_8
        } else {
            outRect.left = DP_8
        }

        outRect.bottom = DP_16

        if (position < 2) {
            outRect.top = DP_16
        }
    }
}