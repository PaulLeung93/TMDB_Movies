package com.example.tmdbmovies.presentation.details

import com.example.tmdbmovies.data.model.MovieDetailResponse

sealed class DetailUiState {
    object Loading : DetailUiState()
    data class Success(val movie: MovieDetailResponse) : DetailUiState()
    data class Error(val message: String) : DetailUiState()
}
