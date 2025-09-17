package com.filimonov.afishamovies.presentation.utils

fun Int.toMovieLengthFormat() : String {
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

    return when(words.size) {
        1 -> this.substring(0, this.lastIndex)
        2 -> "${words[0].substring(0, words[0].lastIndex)} ${words[1]}"
        else -> this
    }
}