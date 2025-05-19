package com.ekyrizky.wikinime.anime.domain

data class Anime(
    val id: Int,
    val title: String,
    val imageUrl: String,
    val studio: String,
    val rating: Double,
    val synopsis: String,
)
