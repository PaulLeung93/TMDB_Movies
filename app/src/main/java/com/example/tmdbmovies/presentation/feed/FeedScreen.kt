package com.example.tmdbmovies.presentation.feed

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
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
    var selectedFilter by remember { mutableStateOf("day") }

    LaunchedEffect(selectedFilter) {
        viewModel.loadTrendingMovies(timeWindow = selectedFilter)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Feed", style = MaterialTheme.typography.titleLarge) },
                modifier = Modifier.statusBarsPadding(),
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            //Most Popular + Filters (Day, Week)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Most Popular",
                    style = MaterialTheme.typography.titleMedium
                )
                Row {
                    FilterChip(
                        selected = selectedFilter == "day",
                        onClick = { selectedFilter = "day" },
                        label = { Text("Today") }
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    FilterChip(
                        selected = selectedFilter == "week",
                        onClick = { selectedFilter = "week" },
                        label = { Text("This Week") }
                    )
                }
            }

            //Movie content
            when (state) {
                is FeedUiState.Loading -> LoadingState()
                is FeedUiState.Error -> ErrorState(
                    message = (state as FeedUiState.Error).message,
                    onRetry = { viewModel.loadTrendingMovies(selectedFilter) }
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
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.fillMaxWidth()
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
            .width(160.dp)
            .clickable(onClick = onClick),
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
                .height(240.dp)
                .fillMaxWidth(),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = movie.title,
            style = MaterialTheme.typography.bodyMedium,
            maxLines = 1
        )

        Text(
            text = "⭐ ${movie.voteAverage}/10",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.secondary
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

