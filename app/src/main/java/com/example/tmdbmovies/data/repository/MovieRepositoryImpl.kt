package com.example.tmdbmovies.data.repository

import com.example.tmdbmovies.data.model.Movie
import com.example.tmdbmovies.data.model.MovieDetailResponse
import com.example.tmdbmovies.data.remote.TmdbApi
import com.example.tmdbmovies.domain.repository.MovieRepository
import javax.inject.Inject

/**
 * Implementation of MovieRepository using the TMDB API.
 */
class MovieRepositoryImpl @Inject constructor(
    private val api: TmdbApi
) : MovieRepository {

    override suspend fun getTrendingMovies(timeWindow: String): List<Movie> {
        return api.getTrendingMovies(timeWindow).movies
    }

    override suspend fun getMovieDetails(movieId: Int): MovieDetailResponse {
        return api.getMovieDetails(movieId)
    }
}
