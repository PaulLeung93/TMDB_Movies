package com.example.tmdbmovies.presentation.feed

import com.example.tmdbmovies.data.model.Movie

/**
 * The UI state for the Home/Feed screen
 */
sealed class FeedUiState {
    object Loading : FeedUiState()
    data class Success(val movies: List<Movie>) : FeedUiState()
    data class Error(val message: String) : FeedUiState()
}
