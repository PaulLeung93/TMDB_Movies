package com.example.tmdbmovies.domain.usecase

import com.example.tmdbmovies.data.model.MovieDetailResponse
import com.example.tmdbmovies.domain.repository.MovieRepository
import javax.inject.Inject

/**
 * Use case to fetch detailed movie info by ID
 */
class GetMovieDetailsUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(movieId: Int): MovieDetailResponse {
        return repository.getMovieDetails(movieId)
    }
}
