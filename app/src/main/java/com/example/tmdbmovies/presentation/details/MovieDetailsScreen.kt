package com.example.tmdbmovies.presentation.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest

@Composable
fun MovieDetailsScreen(
    movieId: Int,
    viewModel: DetailViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(movieId) {
        viewModel.loadMovie(movieId)
    }

    when (state) {
        is DetailUiState.Loading -> LoadingState()
        is DetailUiState.Error -> ErrorState((state as DetailUiState.Error).message)
        is DetailUiState.Success -> {
            val movie = (state as DetailUiState.Success).movie
            MovieDetailContent(
                title = movie.title,
                overview = movie.overview,
                releaseDate = movie.releaseDate,
                voteAverage = movie.voteAverage,
                posterPath = movie.posterPath
            )
        }
    }
}

@Composable
fun MovieDetailContent(
    title: String,
    overview: String?,
    releaseDate: String?,
    voteAverage: Float?,
    posterPath: String?
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        if (posterPath != null) {
            val painter = rememberAsyncImagePainter(
                ImageRequest.Builder(LocalContext.current)
                    .data("https://image.tmdb.org/t/p/w500$posterPath")
                    .crossfade(true)
                    .build()
            )

            Image(
                painter = painter,
                contentDescription = title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))
        }

        Text(text = title, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)

        releaseDate?.let {
            Text(text = "Release Date: $it", style = MaterialTheme.typography.bodySmall)
        }

        voteAverage?.let {
            Text(text = "Rating: $it/10", style = MaterialTheme.typography.bodySmall)
        }

        Spacer(modifier = Modifier.height(12.dp))

        overview?.let {
            Text(text = it, style = MaterialTheme.typography.bodyMedium)
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = { /* TODO: Save to favorites */ }) {
            Text("Add to Favorites")
        }
    }
}

@Composable
fun LoadingState() {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
fun ErrorState(message: String) {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Error loading movie: $message", textAlign = TextAlign.Center)
    }
}
