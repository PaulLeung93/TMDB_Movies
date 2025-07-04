package com.example.tmdbmovies.presentation.feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tmdbmovies.data.model.Movie
import com.example.tmdbmovies.data.repository.FavoritesRepository
import com.example.tmdbmovies.domain.usecase.GetTrendingMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for fetching/exposing trending movies to the UI
 */
@HiltViewModel
class FeedViewModel @Inject constructor(
    private val getTrendingMoviesUseCase: GetTrendingMoviesUseCase,
    private val favoritesRepository: FavoritesRepository

) : ViewModel() {

    private val _uiState = MutableStateFlow<FeedUiState>(FeedUiState.Loading)
    val uiState: StateFlow<FeedUiState> = _uiState

    //Expose all movies separately for reuse in FavoritesScreen
    private val _allMovies = MutableStateFlow<List<Movie>>(emptyList())
    val allMovies: StateFlow<List<Movie>> = _allMovies.asStateFlow()

    //Expose favorites from the repository
    val favoriteIds: StateFlow<Set<Int>> = favoritesRepository.favoriteIds.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        emptySet()
    )

    init {
        //Load movies as soon as the ViewModel is created
        loadTrendingMovies()
    }

    /**
     * Fetch trending movies for a given time window (day/week)
     */
    fun loadTrendingMovies(timeWindow: String = "day") {
        viewModelScope.launch {
            _uiState.value = FeedUiState.Loading
            try {
                val movies = getTrendingMoviesUseCase(timeWindow)
                _allMovies.value = movies
                _uiState.value = FeedUiState.Success(movies)
            } catch (e: Exception) {
                _uiState.value = FeedUiState.Error(e.localizedMessage ?: "Unknown error")
            }
        }
    }
}
