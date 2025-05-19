@file:OptIn(FlowPreview::class)

package com.ekyrizky.wikinime.anime.presentation.anime_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ekyrizky.wikinime.anime.domain.Anime
import com.ekyrizky.wikinime.anime.domain.AnimeRepository
import com.ekyrizky.wikinime.core.domain.onError
import com.ekyrizky.wikinime.core.domain.onSuccess
import com.ekyrizky.wikinime.core.presentation.toUiText
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AnimeListViewModel(
    private val animeRepository: AnimeRepository
) : ViewModel() {

    private var cachedAnime = emptyList<Anime>()
    private var searchJob: Job? = null
    private var observeFavoriteJob: Job? = null

    private val _state = MutableStateFlow<AnimeListState>(AnimeListState())
    val state = _state
        .onStart {
            if (cachedAnime.isEmpty()) {
                observeSearchQuery()
            }
            observeFavoriteStatus()
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _state.value
        )

    fun onAction(action: AnimeListAction) {
        when (action) {
            is AnimeListAction.OnAnimeClick -> {

            }

            is AnimeListAction.OnSearchQueryChange -> {
                _state.update {
                    it.copy(searchQuery = action.query)
                }
            }

            is AnimeListAction.OnTabSelected -> {
                _state.update {
                    it.copy(selectedTabIndex = action.index)
                }
            }
        }
    }

    private fun observeSearchQuery() {
        state
            .map { it.searchQuery }
            .distinctUntilChanged()
            .debounce(500L)
            .onEach { query ->
                when {
                    query.isBlank() -> {
                        _state.update {
                            it.copy(
                                errorMessage = null,
                                searchResults = cachedAnime
                            )
                        }
                    }

                    query.length >= 2 -> {
                        searchJob?.cancel()
                        searchJob = searchAnime(query)
                    }
                }
            }.launchIn(viewModelScope)
    }

    private fun searchAnime(query: String) = viewModelScope.launch {
        _state.update {
            it.copy(
                isLoading = true
            )
        }
        animeRepository
            .searchAnime(query)
            .onSuccess { searchResults ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = null,
                        searchResults = searchResults
                    )
                }
            }
            .onError { error ->
                _state.update {
                    it.copy(
                        searchResults = emptyList(),
                        isLoading = false,
                        errorMessage = error.toUiText()
                    )
                }
            }
    }

    private fun observeFavoriteStatus() {
        observeFavoriteJob?.cancel()
        observeFavoriteJob = animeRepository
            .getFavoriteAnime()
            .onEach { favoriteAnime ->
                _state.update {
                    it.copy(favoriteAnime = favoriteAnime)
                }
            }
            .launchIn(viewModelScope)
    }
}