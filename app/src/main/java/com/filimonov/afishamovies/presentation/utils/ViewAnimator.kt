package com.filimonov.afishamovies.presentation.utils

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
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
        }
    }

    fun setupVisibilityGone(view: View, duration: Long) {
        view.animate()
            .alpha(0f)
            .setDuration(duration)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    view.visibility = View.GONE
                }
            })
    }
}