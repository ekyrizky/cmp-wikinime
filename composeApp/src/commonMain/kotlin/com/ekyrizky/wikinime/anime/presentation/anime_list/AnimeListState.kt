package com.ekyrizky.wikinime.anime.presentation.anime_list

import com.ekyrizky.wikinime.anime.domain.Anime
import com.ekyrizky.wikinime.core.presentation.UiText

data class AnimeListState(
    val searchQuery: String = "one piece",
    val searchResults: List<Anime> = emptyList(),
    val favoriteAnime: List<Anime> = emptyList(),
    val isLoading: Boolean = true,
    val selectedTabIndex: Int = 0,
    val errorMessage: UiText? = null
)