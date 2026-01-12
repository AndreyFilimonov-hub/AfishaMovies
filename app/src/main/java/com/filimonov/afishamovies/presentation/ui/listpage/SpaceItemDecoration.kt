package com.filimonov.afishamovies.presentation.ui.listpage

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.filimonov.afishamovies.presentation.ui.listpage.mediabannergridadapter.MediaBannerGridAdapter

class SpaceItemDecoration(
    private val adapter: MediaBannerGridAdapter,
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
        if (position == RecyclerView.NO_POSITION) return
        val viewType = adapter.getItemViewType(position)

        if (viewType == MediaBannerGridAdapter.LOADING_TYPE || viewType == MediaBannerGridAdapter.ERROR_TYPE) {
            outRect.set(0, 0, 0, 0)
            return
        }

        when {
            position % 2 == 0 -> {
                outRect.set(marginBetween, 0, 0, marginBottom)
            }

            else -> {
                outRect.set(0, 0, marginBetween, marginBottom)
            }
        }
    }
}