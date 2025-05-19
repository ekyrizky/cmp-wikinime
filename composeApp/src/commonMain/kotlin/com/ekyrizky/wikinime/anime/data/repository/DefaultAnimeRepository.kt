package com.ekyrizky.wikinime.anime.data.repository

import androidx.sqlite.SQLiteException
import com.ekyrizky.wikinime.anime.data.database.FavoriteAnimeDao
import com.ekyrizky.wikinime.anime.data.mappers.toAnime
import com.ekyrizky.wikinime.anime.data.mappers.toEntity
import com.ekyrizky.wikinime.anime.data.network.RemoteAnimeDataSource
import com.ekyrizky.wikinime.anime.domain.Anime
import com.ekyrizky.wikinime.anime.domain.AnimeRepository
import com.ekyrizky.wikinime.core.domain.DataError
import com.ekyrizky.wikinime.core.domain.EmptyResult
import com.ekyrizky.wikinime.core.domain.Result
import com.ekyrizky.wikinime.core.domain.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DefaultAnimeRepository(
    private val remoteAnimeDataSource: RemoteAnimeDataSource,
    private val favoriteAnimeDao: FavoriteAnimeDao
) : AnimeRepository {
    override suspend fun searchAnime(query: String): Result<List<Anime>, DataError.Remote> {
        return remoteAnimeDataSource
            .searchAnime(query)
            .map { dto ->
                dto.results.map { it.toAnime() }
            }
    }

    override fun getFavoriteAnime(): Flow<List<Anime>> {
        return favoriteAnimeDao
            .getFavoriteAnime()
            .map { animeEntities ->
                animeEntities.map { it.toAnime() }
            }
    }

    override fun isAnimeFavorite(id: Int): Flow<Boolean> {
        return favoriteAnimeDao
            .getFavoriteAnime()
            .map { animeEntities ->
                animeEntities.any { it.id == id }
            }
    }

    override suspend fun markAsFavorite(anime: Anime): EmptyResult<DataError.Local> {
        return try {
            favoriteAnimeDao.upsert(anime.toEntity())
            Result.Success(Unit)
        } catch (e: SQLiteException) {
            Result.Error(DataError.Local.DISK_FULL)
        }
    }

    override suspend fun deleteFromFavorites(id: Int) {
        favoriteAnimeDao.deleteFavoriteBook(id)
    }
}