package com.ekyrizky.wikinime.anime.data.network

import com.ekyrizky.wikinime.anime.data.dto.SearchResponseDto
import com.ekyrizky.wikinime.core.domain.DataError
import com.ekyrizky.wikinime.core.domain.Result

interface RemoteAnimeDataSource {
    suspend fun searchAnime(
        query: String,
        resultLimit: Int? = null
    ): Result<SearchResponseDto, DataError.Remote>
}