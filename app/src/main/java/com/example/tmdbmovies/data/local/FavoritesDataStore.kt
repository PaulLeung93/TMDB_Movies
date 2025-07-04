package com.example.tmdbmovies.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "favorites_prefs")

class FavoritesDataStore(private val context: Context) {

    private val FAVORITES_KEY = stringSetPreferencesKey("favorite_movie_ids")

    val favoriteIds: Flow<Set<Int>> = context.dataStore.data
        .map { preferences ->
            preferences[FAVORITES_KEY]?.mapNotNull { it.toIntOrNull() }?.toSet() ?: emptySet()
        }

    suspend fun toggleFavorite(movieId: Int) {
        context.dataStore.edit { preferences ->
            val current = preferences[FAVORITES_KEY] ?: emptySet()
            val movieIdString = movieId.toString()
            preferences[FAVORITES_KEY] = if (current.contains(movieIdString)) {
                current - movieIdString
            } else {
                current + movieIdString
            }
        }
    }

    suspend fun isFavorite(movieId: Int): Boolean {
        val current = context.dataStore.data.map {
            it[FAVORITES_KEY]?.mapNotNull { id -> id.toIntOrNull() }?.toSet() ?: emptySet()
        }
        return current.first().contains(movieId)
    }
}
