package com.example.movieapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.entity.Movie
import com.example.movieapp.repo.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(private val movieRepository: MovieRepository) : ViewModel() {
    private var _movies = MutableStateFlow<List<Movie>>(emptyList()) // Using StateFlow
    val movies: StateFlow<List<Movie>> get() = _movies

    // Refresh the movie list
    fun refreshMovies() :List<Movie>{
        viewModelScope.launch {
            val updatedMovies = movieRepository.refreshMovies()  // Assuming this returns the updated list
            _movies.value = updatedMovies  // Update the state
        }
        return _movies.value
    }

    // Toggle favorite status for a movie
    fun toggleFavorite(movie: Movie) {
        viewModelScope.launch {
            movieRepository.toggleFavorite(movie.id, !movie.isFavorite)
            refreshMovies()  // Refresh after updating the favorite status
        }
    }

    init {
        // Initialize by loading movies into the StateFlow
        viewModelScope.launch(Dispatchers.IO) {
            val updatedMovies = movieRepository.refreshMovies() // Fetch movies
            _movies.value = updatedMovies // Update the StateFlow
        }
    }
}
