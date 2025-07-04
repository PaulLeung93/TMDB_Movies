package com.example.tmdbmovies.presentation.favorites

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tmdbmovies.presentation.feed.MovieCard

@Composable
fun FavoritesScreen(
    viewModel: FavoritesViewModel = hiltViewModel()
) {
    val favorites by viewModel.uiState.collectAsState()

    if (favorites.isEmpty()) {
        Text("No favorites yet!")
    } else {
        LazyVerticalGrid(columns = GridCells.Adaptive(128.dp)) {
            items(favorites) { movie ->
                MovieCard(movie = movie)
            }
        }
    }
}
