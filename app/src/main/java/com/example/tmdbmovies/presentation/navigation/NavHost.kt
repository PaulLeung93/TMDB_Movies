package com.example.tmdbmovies.presentation.navigation


import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.tmdbmovies.presentation.details.MovieDetailsScreen
import com.example.tmdbmovies.presentation.feed.FeedScreen
import com.example.tmdbmovies.presentation.favorites.FavoritesScreen
import com.example.tmdbmovies.presentation.feed.FeedViewModel
import com.example.tmdbmovies.presentation.settings.SettingsScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    androidx.navigation.compose.NavHost(
        navController = navController,
        startDestination = NavRoutes.HOME,
        modifier = modifier
    ) {
        composable(NavRoutes.HOME) {
            FeedScreen(navController = navController)
        }

        composable(NavRoutes.FAVORITES) {
            val viewModel: FeedViewModel = hiltViewModel()
            val allMovies by viewModel.allMovies.collectAsState()
            val favoriteIds by viewModel.favoriteIds.collectAsState()

            FavoritesScreen(
                allMovies = allMovies,
                favoriteIds = favoriteIds,
                onMovieClick = { movie ->
                    navController.navigate("details/${movie.id}")
                }
            )
        }

        composable(NavRoutes.SETTINGS) {
            SettingsScreen()
        }
        composable(
            route = NavRoutes.DETAILS,
            arguments = listOf(navArgument("movieId") { type = NavType.IntType })
        ) {
            val movieId = it.arguments?.getInt("movieId") ?: -1
            MovieDetailsScreen(
                movieId = movieId,
                onBack = { navController.popBackStack() }
            )
        }
    }
}


