package com.ekyrizky.wikinime.anime.presentation.anime_list

import com.ekyrizky.wikinime.anime.domain.Anime

sealed interface AnimeListAction {
    data class OnSearchQueryChange(val query: String): AnimeListAction
    data class OnAnimeClick(val anime: Anime): AnimeListAction
    data class OnTabSelected(val index: Int): AnimeListAction
}