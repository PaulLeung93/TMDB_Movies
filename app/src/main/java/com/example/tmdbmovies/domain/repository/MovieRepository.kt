package com.example.tmdbmovies.domain.repository

import com.example.tmdbmovies.data.model.Movie
import com.example.tmdbmovies.data.model.MovieDetailResponse

/**
 * Defines functions related to movie data
 */
interface MovieRepository {
    suspend fun getTrendingMovies(timeWindow: String): List<Movie>
    suspend fun getMovieDetails(movieId: Int): MovieDetailResponse
}
