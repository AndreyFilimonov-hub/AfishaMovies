package com.filimonov.afishamovies.presentation.utils

import android.widget.ImageView
import com.bumptech.glide.Glide

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

fun ImageView.loadImage(url: String?) {
    Glide.with(this).load(url).into(this)
}