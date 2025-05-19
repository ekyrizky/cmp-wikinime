package com.ekyrizky.wikinime.anime.presentation.anime_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.ekyrizky.wikinime.anime.domain.AnimeRepository
import com.ekyrizky.wikinime.app.Route
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AnimeDetailViewModel(
    private val animeRepository: AnimeRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val animeId = savedStateHandle.toRoute<Route.AnimeDetail>().id

    private val _state = MutableStateFlow(AnimeDetailState())
    val state = _state
        .onStart {
            observeFavoriteStatus()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _state.value
        )

    fun onAction(action: AnimeDetailAction) {
        when (action) {
            is AnimeDetailAction.OnSelectedAnimeChange -> {
                _state.update {
                    it.copy(
                        anime = action.anime
                    )
                }
            }

            is AnimeDetailAction.OnFavoriteClick -> {
                viewModelScope.launch {
                    if (state.value.isFavorite) {
                        animeRepository.deleteFromFavorites(animeId)
                    } else {
                        state.value.anime?.let { anime ->
                            animeRepository.markAsFavorite(anime)
                        }
                    }
                }
            }

            else -> Unit
        }
    }

    private fun observeFavoriteStatus() {
        animeRepository
            .isAnimeFavorite(animeId)
            .onEach { isFavorite ->
                _state.update {
                    it.copy(isFavorite = isFavorite)
                }
            }
            .launchIn(viewModelScope)
    }
}