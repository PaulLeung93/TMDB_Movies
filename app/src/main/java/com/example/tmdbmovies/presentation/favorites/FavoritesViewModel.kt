package com.example.tmdbmovies.presentation.favorites

import androidx.lifecycle.ViewModel
import com.example.tmdbmovies.data.model.Movie
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * ViewModel for managing user's favorite movies
 */
class FavoritesViewModel : ViewModel() {

    private val _uiState = MutableStateFlow<List<Movie>>(mockFavorites)
    val uiState: StateFlow<List<Movie>> = _uiState
}

//Sample mock favorite movies
private val mockFavorites = listOf(
    Movie(
        id = 101,
        title = "Superman",
        posterPath = "/SampleImage1.jpg",
        releaseDate = "2020-1-01",
        voteAverage = 10.0f
    ),
    Movie(
        id = 102,
        title = "BatMan",
        posterPath = "/SampleImage2.jpg",
        releaseDate = "2020-01-01",
        voteAverage = 2.0f
    )
)
