package com.ekyrizky.wikinime.app

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.ekyrizky.wikinime.anime.presentation.SelectedAnimeViewModel
import com.ekyrizky.wikinime.anime.presentation.anime_detail.AnimeDetailAction
import com.ekyrizky.wikinime.anime.presentation.anime_detail.AnimeDetailScreenRoot
import com.ekyrizky.wikinime.anime.presentation.anime_detail.AnimeDetailViewModel
import com.ekyrizky.wikinime.anime.presentation.anime_list.AnimeListScreenRoot
import com.ekyrizky.wikinime.anime.presentation.anime_list.AnimeListViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
@Preview
fun App() {
    MaterialTheme {
        val navController = rememberNavController()
        NavHost(
            navController = navController,
            startDestination = Route.AnimeGraph
        ) {
            navigation<Route.AnimeGraph>(
                startDestination = Route.AnimeList
            ) {
                composable<Route.AnimeList>(
                    exitTransition = { slideOutHorizontally() },
                    popEnterTransition = { slideInHorizontally() }
                ) {
                    val selectedAnimeViewModel = it.sharedKoinViewModel<SelectedAnimeViewModel>(navController)
                    val viewModel = koinViewModel<AnimeListViewModel>()

                    LaunchedEffect(true) {
                        selectedAnimeViewModel.onSelectedAnime(null)
                    }

                    AnimeListScreenRoot(
                        viewModel = viewModel,
                        onAnimeClick = { anime ->
                            selectedAnimeViewModel.onSelectedAnime(anime)
                            navController.navigate(Route.AnimeDetail(anime.id))
                        }
                    )
                }

                composable<Route.AnimeDetail>(
                    enterTransition = { slideInHorizontally { initialOffset ->
                        initialOffset
                    } },
                    exitTransition = { slideOutHorizontally { initialOffset ->
                        initialOffset
                    } }
                ) {
                    val selectedAnimeViewModel = it.sharedKoinViewModel<SelectedAnimeViewModel>(navController)
                    val viewModel = koinViewModel<AnimeDetailViewModel>()
                    val selectedAnime by selectedAnimeViewModel.selectedAnime.collectAsStateWithLifecycle()

                    LaunchedEffect(selectedAnime) {
                        selectedAnime?.let {
                            viewModel.onAction(AnimeDetailAction.OnSelectedAnimeChange(it))
                        }
                    }

                    AnimeDetailScreenRoot(
                        viewModel = viewModel,
                        onBackClick = {
                            navController.navigateUp()
                        }
                    )
                }
            }
        }
    }
}

@Composable
private inline fun <reified T : ViewModel> NavBackStackEntry.sharedKoinViewModel(
    navController: NavController
): T {
    val navGraphRoute = destination.parent?.route ?: return koinViewModel<T>()
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }
    return koinViewModel(
        viewModelStoreOwner = parentEntry
    )
}