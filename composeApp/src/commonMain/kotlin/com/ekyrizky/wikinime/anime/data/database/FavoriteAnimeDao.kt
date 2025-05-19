package com.ekyrizky.wikinime.anime.data.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.ekyrizky.wikinime.anime.domain.Anime
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteAnimeDao {

    @Upsert
    suspend fun upsert(anime: AnimeEntity)

    @Query("SELECT * FROM AnimeEntity ")
    fun getFavoriteAnime(): Flow<List<AnimeEntity>>

    @Query("SELECT * FROM AnimeEntity WHERE id = :id")
    suspend fun getFavoriteAnimeById(id: Int): Anime

    @Query("DELETE FROM AnimeEntity WHERE id = :id")
    suspend fun deleteFavoriteBook(id: Int)
}