package com.ekyrizky.wikinime.anime.data.database

import androidx.room.RoomDatabase

expect class DatabaseFactory {
    fun create(): RoomDatabase.Builder<FavoriteAnimeDatabase>
}