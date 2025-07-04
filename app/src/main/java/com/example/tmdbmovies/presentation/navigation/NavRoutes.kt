package com.example.tmdbmovies.presentation.navigation

/**
 * Defines routes for each screen.
 */
object NavRoutes {
    const val HOME = "home"
    const val FAVORITES = "favorites"
    const val SETTINGS = "settings"
    const val DETAILS = "details/{movieId}"

    fun detailsRoute(movieId: Int) = "details/$movieId"
}
