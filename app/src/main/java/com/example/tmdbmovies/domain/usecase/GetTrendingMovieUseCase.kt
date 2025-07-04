package com.example.tmdbmovies.domain.usecase

import com.example.tmdbmovies.data.model.Movie
import com.example.tmdbmovies.domain.repository.MovieRepository
import javax.inject.Inject

/**
 * Use case to fetch trending movies from the repository
 */
class GetTrendingMoviesUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(timeWindow: String): List<Movie> {
        return repository.getTrendingMovies(timeWindow)
    }
}
