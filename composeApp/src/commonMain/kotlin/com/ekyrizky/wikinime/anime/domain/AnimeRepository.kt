package com.ekyrizky.wikinime.anime.domain

import com.ekyrizky.wikinime.core.domain.DataError
import com.ekyrizky.wikinime.core.domain.EmptyResult
import com.ekyrizky.wikinime.core.domain.Result
import kotlinx.coroutines.flow.Flow

interface AnimeRepository {
    suspend fun searchAnime(query: String): Result<List<Anime>, DataError.Remote>
    fun getFavoriteAnime(): Flow<List<Anime>>
    fun isAnimeFavorite(id: Int): Flow<Boolean>
    suspend fun markAsFavorite(anime: Anime): EmptyResult<DataError.Local>
    suspend fun deleteFromFavorites(id: Int)
}