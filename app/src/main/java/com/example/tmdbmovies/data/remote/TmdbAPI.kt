package com.example.tmdbmovies.data.remote

import com.example.tmdbmovies.data.model.MovieDetailResponse
import com.example.tmdbmovies.data.model.MovieListResponse
import com.example.tmdbmovies.BuildConfig

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Retrofit interface that defines TMDB API endpoints.
 */
interface TmdbApi {

    /**
     * Fetch trending movies, to be filtered by either Day or Week
     *
     * @param timeWindow day or week
     * @param apiKey TMDB API key
     */
    @GET("trending/movie/{time_window}")
    suspend fun getTrendingMovies(
        @Path("time_window") timeWindow: String,
        @Query("api_key") apiKey: String = BuildConfig.TMDB_API_KEY
    ): MovieListResponse

    /**
     * Fetch detailed info for a specific movie by its ID.
     *
     * @param movieId TMDB movie ID
     * @param apiKey Your TMDB API key
     */
    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String = BuildConfig.TMDB_API_KEY
    ): MovieDetailResponse
}
