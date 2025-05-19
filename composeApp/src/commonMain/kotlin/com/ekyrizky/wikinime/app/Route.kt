package com.ekyrizky.wikinime.app

import kotlinx.serialization.Serializable

sealed interface Route {

    @Serializable
    data object AnimeGraph: Route

    @Serializable
    data object AnimeList: Route

    @Serializable
    data class AnimeDetail(val id: Int): Route
}