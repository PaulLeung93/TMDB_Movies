package com.example.tmdbmovies.presentation.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tmdbmovies.data.repository.FavoritesRepository
import com.example.tmdbmovies.domain.usecase.GetMovieDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val favoritesRepository: FavoritesRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<DetailUiState>(DetailUiState.Loading)
    val uiState: StateFlow<DetailUiState> = _uiState

    private val _isFavorite = MutableStateFlow(false)
    val isFavorite: StateFlow<Boolean> = _isFavorite.asStateFlow()

    private var currentMovieId: Int? = null

    fun loadMovie(movieId: Int) {
        viewModelScope.launch {
            currentMovieId = movieId
            _uiState.value = DetailUiState.Loading
            try {
                val movie = getMovieDetailsUseCase(movieId)
                _uiState.value = DetailUiState.Success(movie)
                _isFavorite.value = favoritesRepository.isFavorite(movieId)
            } catch (e: Exception) {
                _uiState.value = DetailUiState.Error(e.localizedMessage ?: "Unknown error")
            }
        }
    }

    fun toggleFavorite() {
        currentMovieId?.let { movieId ->
            viewModelScope.launch {
                favoritesRepository.toggleFavorite(movieId)
                _isFavorite.value = !_isFavorite.value
            }
        }
    }
}
