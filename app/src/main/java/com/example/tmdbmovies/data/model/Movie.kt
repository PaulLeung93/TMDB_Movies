package com.example.tmdbmovies.data.model

import com.google.gson.annotations.SerializedName

/**
 * Response returned when fetching trending movies
 */
data class MovieListResponse(
    @SerializedName("results") val movies: List<Movie>
)

/**
 * Represents a basic movie item to be displayed in a list
 */
data class Movie(
    val id: Int,
    val title: String,

    //Nullable (some moies may not have a poster)
    @SerializedName("poster_path") val posterPath: String?,

    @SerializedName("release_date") val releaseDate: String?,

    @SerializedName("vote_average") val voteAverage: Float?
)
