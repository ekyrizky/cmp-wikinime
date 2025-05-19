package com.ekyrizky.wikinime.anime.presentation

import androidx.lifecycle.ViewModel
import com.ekyrizky.wikinime.anime.domain.Anime
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SelectedAnimeViewModel : ViewModel() {

    private val _selectedAnime = MutableStateFlow<Anime?>(null)
    val selectedAnime = _selectedAnime.asStateFlow()

    fun onSelectedAnime(anime: Anime?) {
        _selectedAnime.value = anime
    }
}