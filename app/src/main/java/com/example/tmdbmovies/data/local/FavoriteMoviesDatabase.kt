package com.example.tmdbmovies.data.local


import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.tmdbmovies.data.local.dao.FavoriteMovieDao
import com.example.tmdbmovies.data.local.entity.FavoriteMovieEntity

@Database(
    entities = [FavoriteMovieEntity::class],
    version = 1,
    exportSchema = false
)
abstract class FavoriteMoviesDatabase : RoomDatabase() {
    abstract fun favoriteMovieDao(): FavoriteMovieDao
}
