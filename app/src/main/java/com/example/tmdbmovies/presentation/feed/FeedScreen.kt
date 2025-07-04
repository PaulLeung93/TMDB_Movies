package com.example.tmdbmovies.presentation.feed

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.tmdbmovies.data.model.Movie
import com.example.tmdbmovies.presentation.navigation.NavRoutes
import com.example.tmdbmovies.ui.theme.TMDBMoviesTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedScreen(
    viewModel: FeedViewModel = hiltViewModel(),
    navController: NavController
) {
    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadTrendingMovies()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Feed", style = MaterialTheme.typography.titleLarge) },
                modifier = Modifier
                    .statusBarsPadding(),
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            when (state) {
                is FeedUiState.Loading -> LoadingState()
                is FeedUiState.Error -> ErrorState(
                    message = (state as FeedUiState.Error).message,
                    onRetry = { viewModel.loadTrendingMovies() }
                )
                is FeedUiState.Success -> SuccessState(
                    movies = (state as FeedUiState.Success).movies,
                    onMovieClick = { movieId ->
                        navController.navigate(NavRoutes.detailsRoute(movieId))
                    }
                )
            }
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
fun ErrorState(message: String, onRetry: () -> Unit) {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Error: $message", textAlign = TextAlign.Center)
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = onRetry) {
                Text("Retry")
            }
        }
    }
}

@Composable
fun SuccessState(
    movies: List<Movie>,
    onMovieClick: (Int) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 128.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        contentPadding = PaddingValues(8.dp)
    ) {
        items(movies) { movie ->
            MovieCard(movie = movie, onClick = { onMovieClick(movie.id) })
        }
    }
}


@Composable
fun MovieCard(
    movie: Movie,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable { onClick() }, // 👈 Add clickable modifier
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val imageUrl = "https://image.tmdb.org/t/p/w500${movie.posterPath}"
        val painter = rememberAsyncImagePainter(
            model = ImageRequest.Builder(LocalContext.current)
                .data(imageUrl)
                .crossfade(true)
                .build()
        )

        Image(
            painter = painter,
            contentDescription = movie.title,
            modifier = Modifier
                .height(180.dp)
                .fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = movie.title,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center
        )
    }
}


//@Preview(showBackground = true)
//@Composable
//fun MovieCardPreview() {
//    val mockMovie = Movie(
//        id = 1,
//        title = "Superman",
//        posterPath = "/Sample.jpg",
//        releaseDate = "2025-07-11",
//        voteAverage = 10.0f
//    )
//
//    TMDBMoviesTheme {
//        MovieCard(movie = mockMovie)
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//fun SuccessStatePreview() {
//    val mockMovies = List(6) {
//        Movie(
//            id = it,
//            title = "Movie $it",
//            posterPath = "/Sample.jpg",
//            releaseDate = "2025-01-01",
//            voteAverage = 5.0f
//        )
//    }
//
//    TMDBMoviesTheme {
//        SuccessState(movies = mockMovies)
//    }
//}

