package com.ekyrizky.wikinime.anime.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AnimeDto(
    @SerialName("mal_id") val id: Int,
    @SerialName("title") val title: String?,
    @SerialName("images") val images: AnimeImages?,
    @SerialName("studios") val studios: List<Studio>?,
    @SerialName("score") val rating: Double?,
    @SerialName("synopsis") val synopsis: String?,
)

@Serializable
data class AnimeImages(
    @SerialName("jpg") val jpg: ImageUrl,
    @SerialName("webp") val webp: ImageUrl
)

@Serializable
data class ImageUrl(
    @SerialName("image_url") val imageUrl: String,
    @SerialName("small_image_url") val smallImageUrl: String,
    @SerialName("large_image_url") val largeImageUrl: String,
)

@Serializable
data class Studio(
    @SerialName("mal_id") val id: Int,
    @SerialName("type") val type: String,
    @SerialName("name") val name: String,
    @SerialName("url") val url: String,
)