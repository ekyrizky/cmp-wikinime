package com.ekyrizky.wikinime.anime.data.mappers

import com.ekyrizky.wikinime.anime.data.database.AnimeEntity
import com.ekyrizky.wikinime.anime.data.dto.AnimeDto
import com.ekyrizky.wikinime.anime.domain.Anime

fun AnimeDto.toAnime(): Anime {
    return Anime(
        id = id,
        title = title.orEmpty(),
        imageUrl = images?.jpg?.imageUrl.orEmpty(),
        studio = studios?.getOrNull(0)?.name.orEmpty(),
        rating = rating ?: 0.0,
        synopsis = synopsis.orEmpty()
    )
}

fun Anime.toEntity(): AnimeEntity {
    return AnimeEntity(
        id = id,
        title = title,
        imageUrl = imageUrl,
        studio = studio,
        rating = rating,
        synopsis = synopsis
    )
}

fun AnimeEntity.toAnime(): Anime {
    return Anime(
        id = id,
        title = title,
        imageUrl = imageUrl,
        studio = studio,
        rating = rating,
        synopsis = synopsis
    )
}