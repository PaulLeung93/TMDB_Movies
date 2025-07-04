package com.example.tmdbmovies.data.model

import com.google.gson.annotations.SerializedName

/**
 * Movie Details returned by /movie/{id} endpoint.
 */
data class MovieDetailResponse(
    val id: Int,
    val title: String,
    val overview: String,

    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("release_date") val releaseDate: String?,
    val runtime: Int?,
    val genres: List<Genre>,

    @SerializedName("vote_average") val voteAverage: Float?
)

/**
 * The movie's genre
 */
data class Genre(
    val id: Int,
    val name: String
)
