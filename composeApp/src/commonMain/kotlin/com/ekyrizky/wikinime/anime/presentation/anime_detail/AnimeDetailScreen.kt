package com.ekyrizky.wikinime.anime.presentation.anime_detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ekyrizky.wikinime.anime.presentation.anime_detail.components.AnimeChip
import com.ekyrizky.wikinime.anime.presentation.anime_detail.components.BlurredImageBackground
import com.ekyrizky.wikinime.core.presentation.SandYellow
import org.jetbrains.compose.resources.stringResource
import wikinime.composeapp.generated.resources.Res
import wikinime.composeapp.generated.resources.description_unavailable
import wikinime.composeapp.generated.resources.synopsis
import kotlin.math.round

@Composable
fun AnimeDetailScreenRoot(
    viewModel: AnimeDetailViewModel,
    onBackClick: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    BookDetailScreen(
        state = state,
        onAction = { action ->
            when (action) {
                is AnimeDetailAction.OnBackClick -> onBackClick()
                else -> Unit
            }
            viewModel.onAction(action)
        }
    )
}

@Composable
private fun BookDetailScreen(
    state: AnimeDetailState,
    onAction: (AnimeDetailAction) -> Unit
) {
    BlurredImageBackground(
        imageUrl = state.anime?.imageUrl,
        isFavorite = state.isFavorite,
        onFavoriteClick = {
            onAction(AnimeDetailAction.OnFavoriteClick)
        },
        onBackClick = {
            onAction(AnimeDetailAction.OnBackClick)
        },
        modifier = Modifier.fillMaxSize()
    ) {
        if (state.anime != null) {
            Column(
                modifier = Modifier
                    .widthIn(max = 700.dp)
                    .fillMaxWidth()
                    .padding(
                        vertical = 16.dp,
                        horizontal = 24.dp
                    )
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = state.anime.title,
                    style = MaterialTheme.typography.headlineSmall,
                    textAlign = TextAlign.Center
                )

                Text(
                    text = state.anime.studio,
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center
                )

                AnimeChip(
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                ) {
                    Text(text = "${round(state.anime.rating * 10) / 10.0}")
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = SandYellow
                    )
                }

                Text(
                    text = stringResource(Res.string.synopsis),
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier
                        .align(Alignment.Start)
                        .fillMaxWidth()
                        .padding(
                            top = 24.dp,
                            bottom = 8.dp
                        )
                )

                Text(
                    text = if (state.anime.synopsis.isBlank()) {
                        stringResource(Res.string.description_unavailable)
                    } else {
                        state.anime.synopsis
                    },
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Justify,
                    color = if (state.anime.synopsis.isBlank()) {
                        Color.Black.copy(alpha = 0.4f)
                    } else Color.Black,
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                )
            }
        }
    }
}