package com.ekyrizky.wikinime

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.ekyrizky.wikinime.anime.domain.Anime
import com.ekyrizky.wikinime.anime.presentation.anime_list.AnimeListScreen
import com.ekyrizky.wikinime.anime.presentation.anime_list.AnimeListState
import com.ekyrizky.wikinime.anime.presentation.anime_list.components.AnimeSearchBar

@Preview
@Composable
private fun AnimeSearchBarPreview() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
    ) {
        AnimeSearchBar(
            searchQuery = "Anime",
            onSearchQueryChange = {},
            onImeSearch = {},
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}

private val anime = (1..100).map {
    Anime(
        id = it,
        title = "Anime $it",
        imageUrl = "https://example.com",
        studio = "Toei Animation",
        rating = 8.5,
        synopsis = "Synopsis $it"
    )
}

@Preview
@Composable
private fun AnimeListScreenPreview() {
    AnimeListScreen(
        state = AnimeListState(
            searchResults = anime
        ),
        onAction = {}
    )
}