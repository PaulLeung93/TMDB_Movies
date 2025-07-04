package com.example.tmdbmovies.presentation.feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tmdbmovies.domain.usecase.GetTrendingMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for fetching/exposing trending movies to the UI
 */
@HiltViewModel
class FeedViewModel @Inject constructor(
    private val getTrendingMoviesUseCase: GetTrendingMoviesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<FeedUiState>(FeedUiState.Loading)
    val uiState: StateFlow<FeedUiState> = _uiState

    /**
     * Fetch trending movies for a given time window (day/week)
     */
    fun loadTrendingMovies(timeWindow: String = "day") {
        viewModelScope.launch {
            _uiState.value = FeedUiState.Loading
            try {
                val movies = getTrendingMoviesUseCase(timeWindow)
                _uiState.value = FeedUiState.Success(movies)
            } catch (e: Exception) {
                _uiState.value = FeedUiState.Error(e.localizedMessage ?: "Unknown error")
            }
        }
    }
}
