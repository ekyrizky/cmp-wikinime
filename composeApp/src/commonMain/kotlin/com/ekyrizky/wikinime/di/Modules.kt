package com.ekyrizky.wikinime.di

import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.ekyrizky.wikinime.anime.data.database.DatabaseFactory
import com.ekyrizky.wikinime.anime.data.database.FavoriteAnimeDatabase
import com.ekyrizky.wikinime.anime.data.network.KtorRemoteAnimeDataSource
import com.ekyrizky.wikinime.anime.data.network.RemoteAnimeDataSource
import com.ekyrizky.wikinime.anime.data.repository.DefaultAnimeRepository
import com.ekyrizky.wikinime.anime.domain.AnimeRepository
import com.ekyrizky.wikinime.anime.presentation.SelectedAnimeViewModel
import com.ekyrizky.wikinime.anime.presentation.anime_detail.AnimeDetailViewModel
import com.ekyrizky.wikinime.anime.presentation.anime_list.AnimeListViewModel
import com.ekyrizky.wikinime.core.data.HttpClientFactory
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

expect val platformModule: Module

val sharedModule = module {
    single { HttpClientFactory.create(get()) }
    singleOf(::KtorRemoteAnimeDataSource).bind<RemoteAnimeDataSource>()
    singleOf(::DefaultAnimeRepository).bind<AnimeRepository>()

    single {
        get<DatabaseFactory>().create()
            .setDriver(BundledSQLiteDriver())
            .build()
    }
    single { get<FavoriteAnimeDatabase>().favoriteAnimeDao }

    viewModelOf(::SelectedAnimeViewModel)
    viewModelOf(::AnimeListViewModel)
    viewModelOf(::AnimeDetailViewModel)
}