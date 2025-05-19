package com.ekyrizky.wikinime.anime.data.network

import com.ekyrizky.wikinime.anime.data.dto.SearchResponseDto
import com.ekyrizky.wikinime.core.data.safeCall
import com.ekyrizky.wikinime.core.domain.DataError
import com.ekyrizky.wikinime.core.domain.Result
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter

private const val BASE_URL = "https://api.jikan.moe/v4/anime"

class KtorRemoteAnimeDataSource(
    private val httpClient: HttpClient
) : RemoteAnimeDataSource {
    override suspend fun searchAnime(
        query: String,
        resultLimit: Int?
    ): Result<SearchResponseDto, DataError.Remote> {
        return safeCall<SearchResponseDto> {
            httpClient.get(
                urlString = BASE_URL
            ) {
                parameter("q", query)
            }
        }
    }
}