package com.example.tmdbmovies.data.repository

import com.example.tmdbmovies.data.local.FavoritesDataStore
import kotlinx.coroutines.flow.Flow

class FavoritesRepository(
    private val dataStore: FavoritesDataStore
) {
    val favoriteIds: Flow<Set<Int>> = dataStore.favoriteIds

    suspend fun toggleFavorite(movieId: Int) {
        dataStore.toggleFavorite(movieId)
    }

    suspend fun isFavorite(movieId: Int): Boolean {
        return dataStore.isFavorite(movieId)
    }
}
