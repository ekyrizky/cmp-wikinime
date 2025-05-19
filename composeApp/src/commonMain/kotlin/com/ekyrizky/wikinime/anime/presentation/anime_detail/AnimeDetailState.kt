package com.ekyrizky.wikinime.anime.presentation.anime_detail

import com.ekyrizky.wikinime.anime.domain.Anime

data class AnimeDetailState(
    val isLoading: Boolean = true,
    val isFavorite: Boolean = false,
    val anime: Anime? = null
)