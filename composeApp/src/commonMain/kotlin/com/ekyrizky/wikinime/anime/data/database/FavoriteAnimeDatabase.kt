package com.ekyrizky.wikinime.anime.data.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [AnimeEntity::class],
    version = 1
)
@ConstructedBy(AnimeDatabaseConstructor::class)
abstract class FavoriteAnimeDatabase : RoomDatabase() {
    abstract val favoriteAnimeDao: FavoriteAnimeDao

    companion object {
        const val DB_NAME = "anime.db"
    }
}