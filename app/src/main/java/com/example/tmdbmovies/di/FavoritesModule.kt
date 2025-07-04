package com.example.tmdbmovies.di

import android.content.Context
import com.example.tmdbmovies.data.local.FavoritesDataStore
import com.example.tmdbmovies.data.repository.FavoritesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FavoritesModule {

    @Provides
    @Singleton
    fun provideFavoritesDataStore(@ApplicationContext context: Context): FavoritesDataStore {
        return FavoritesDataStore(context)
    }

    @Provides
    @Singleton
    fun provideFavoritesRepository(dataStore: FavoritesDataStore): FavoritesRepository {
        return FavoritesRepository(dataStore)
    }
}
