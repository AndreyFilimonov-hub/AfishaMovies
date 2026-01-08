package com.filimonov.afishamovies.presentation.utils

import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.get
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.widget.NestedScrollView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.filimonov.afishamovies.presentation.ui.filmpage.FilmPageFragment

fun Int.toMovieLengthFormat(): String {
    val hours = this / 60
    val minutes = this - hours * 60
    return if (hours == 0) {
        "$this мин"
    } else if (minutes != 0) {
        "$hours ч $minutes мин"
    } else {
        "$hours ч"
    }
}

fun String.cutWordEnd(): String {
    val words = this.trim().split(" ")

    return when (words.size) {
        1 -> this.substring(0, this.lastIndex)
        2 -> "${words[0].substring(0, words[0].lastIndex)} ${words[1]}"
        else -> this
    }
}

fun Double.roundRating(): String {
    return this.toString().substring(0, 3)
}

fun ImageView.loadImageBigPoster(url: String?, onPosterColorReady: (dominantColor: Int) -> Unit) {
    Glide.with(this).load(url).into(object : CustomTarget<Drawable>() {
        override fun onResourceReady(
            resource: Drawable,
            transition: Transition<in Drawable>?
        ) {
            this@loadImageBigPoster.setImageDrawable(resource)

            val bitmap = (resource as BitmapDrawable).bitmap
            val topColor = bitmap.getTopAverageColor()

            onPosterColorReady(topColor)
        }

        override fun onLoadCleared(placeholder: Drawable?) {}
    })
}

fun ImageView.loadImageBanner(url: String?) {
    Glide.with(this)
        .load(url)
        .transform(RoundedCorners((4 * context.resources.displayMetrics.density).toInt()))
        .into(this)
}

private fun Bitmap.getTopAverageColor(rows: Int = 24): Int {
    val actualRows = rows.coerceAtMost(height)
    var r = 0L
    var g = 0L
    var b = 0L
    var count = 0

    for (y in 0 until actualRows) {
        for (x in 0 until width) {
            val pixel = this[x, y]
            r += Color.red(pixel)
            g += Color.green(pixel)
            b += Color.blue(pixel)
            count++
        }
    }

    return Color.rgb((r / count).toInt(), (g / count).toInt(), (b / count).toInt())
}

fun FilmPageFragment.setupDynamicStatusBar(
    activity: AppCompatActivity,
    posterImageView: ImageView,
    scrollView: NestedScrollView,
    posterUrl: String?,
    detailBackgroundColor: Int = Color.WHITE
) {
    var posterDominantColor: Int = Color.BLACK

    posterImageView.loadImageBigPoster(posterUrl) { dominantColor ->
        posterDominantColor = dominantColor
        updateStatusBarColor(
            activity,
            posterDominantColor,
            scrollY = 0,
            posterImageView.height,
            detailBackgroundColor
        )
    }

    scrollView.setOnScrollChangeListener { _, _, scrollY, _, _ ->
        updateStatusBarColor(
            activity,
            posterDominantColor,
            scrollY,
            posterImageView.height,
            detailBackgroundColor
        )
    }
}

private fun updateStatusBarColor(
    activity: AppCompatActivity,
    posterColor: Int,
    scrollY: Int,
    posterHeight: Int,
    detailBackgroundColor: Int
) {
    val colorUnderStatusBar = if (scrollY < posterHeight) posterColor else detailBackgroundColor

    val brightness = (0.299 * Color.red(colorUnderStatusBar) +
            0.587 * Color.green(colorUnderStatusBar) +
            0.114 * Color.blue(colorUnderStatusBar))
    val lightIcons = brightness > 186
    WindowInsetsControllerCompat(activity.window, activity.window.decorView)
        .isAppearanceLightStatusBars = lightIcons
}