package com.filimonov.afishamovies.domain.enums

enum class TypeImage(val typeNames: List<String>) {
    FRAME(listOf("frame", "still", "screenshot")),
    BACKSTAGE(listOf("shooting", "promo")),
    POSTER(listOf("cover", "wallpaper", "backdrops"))
}