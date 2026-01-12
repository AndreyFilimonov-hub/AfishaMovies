package com.filimonov.afishamovies.presentation.utils

import android.view.View

class ViewAnimator {

    fun setupVisibilityVisible(view: View, duration: Long) {
        with(view) {
            alpha = 0f
            visibility = View.VISIBLE

            animate()
                .alpha(1f)
                .setDuration(duration)
                .setListener(null)
                .start()
        }
    }

    fun setupVisibilityGone(view: View, duration: Long) {
        view.animate()
            .alpha(0f)
            .setDuration(duration)
            .withEndAction {
                view.visibility = View.GONE
            }
            .start()
    }
}