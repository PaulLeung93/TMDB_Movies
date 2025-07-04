package com.example.tmdbmovies.di

import android.content.Context
import androidx.room.Room
import com.example.tmdbmovies.data.local.FavoriteMoviesDatabase
import com.example.tmdbmovies.data.local.dao.FavoriteMovieDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(appContext: Context): FavoriteMoviesDatabase {
        return Room.databaseBuilder(
            appContext,
            FavoriteMoviesDatabase::class.java,
            "favorite_movies_db"
        ).build()
    }

    @Provides
    fun provideFavoriteMovieDao(db: FavoriteMoviesDatabase): FavoriteMovieDao {
        return db.favoriteMovieDao()
    }
}
