package com.ekyrizky.wikinime.anime.presentation.anime_detail

import com.ekyrizky.wikinime.anime.domain.Anime

sealed interface AnimeDetailAction {
    data object OnBackClick: AnimeDetailAction
    data object OnFavoriteClick: AnimeDetailAction
    data class OnSelectedAnimeChange(val anime: Anime): AnimeDetailAction
}