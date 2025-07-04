package com.example.tmdbmovies.presentation.navigation


import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
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
        composable(NavRoutes.HOME) { FeedScreen() }
        composable(NavRoutes.FAVORITES) { FavoritesScreen() }
        composable(NavRoutes.SETTINGS) { SettingsScreen() }
    }
}

