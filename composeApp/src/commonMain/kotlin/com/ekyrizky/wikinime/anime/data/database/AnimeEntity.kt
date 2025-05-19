package com.ekyrizky.wikinime.anime.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class AnimeEntity(
    @PrimaryKey(autoGenerate = false) val id: Int,
    val title: String,
    val imageUrl: String,
    val studio: String,
    val rating: Double,
    val synopsis: String,
)
