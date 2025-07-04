package com.example.tmdbmovies.presentation.navigation


import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.tmdbmovies.presentation.details.MovieDetailsScreen
import com.example.tmdbmovies.presentation.feed.FeedScreen
import com.example.tmdbmovies.presentation.favorites.FavoritesScreen
import com.example.tmdbmovies.presentation.settings.SettingsScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = NavRoutes.HOME,
        modifier = modifier
    ) {
        composable(NavRoutes.HOME) {
            FeedScreen(navController = navController)
        }
        composable(NavRoutes.FAVORITES) { FavoritesScreen() }
        composable(NavRoutes.SETTINGS) { SettingsScreen() }

        composable(
            route = NavRoutes.DETAILS,
            arguments = listOf(navArgument("movieId") { type = NavType.IntType })
        ) { backStackEntry ->
            val movieId = backStackEntry.arguments?.getInt("movieId") ?: -1
            MovieDetailsScreen(movieId = movieId)
        }

    }
}

