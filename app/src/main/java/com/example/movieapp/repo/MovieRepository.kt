package com.example.movieapp.repo

import com.example.movieapp.BuildConfig
import com.example.movieapp.dao.MovieDao
import com.example.movieapp.entity.Movie
import com.example.movieapp.mapper.toMovie
import com.example.movieapp.service.TMDBApiService
import javax.inject.Inject


class MovieRepository @Inject constructor(
    private val movieDao: MovieDao,
    private val tmbdApiService: TMDBApiService
) {
    private val apiKey = BuildConfig.api_key


    suspend fun refreshMovies() : List<Movie> {
    val  moviesFromApi = tmbdApiService.getPopularMovies(apiKey,1).movies.map { it.toMovie() } // Assume this fetches a list of ApiMovie
    val localMovies = movieDao.getAllMoviesSync()
    // Fetch movies from API and save them to the database
    val updatedMovies = moviesFromApi.map { apiMovie ->
        val localMovie = localMovies.find { it.id == apiMovie.id }
        Movie(
            id = apiMovie.id,
            title = apiMovie.title,
            overView = apiMovie.overView,
            posterUrl = apiMovie.posterUrl,
            rating = apiMovie.rating,
            isFavorite = localMovie?.isFavorite ?: false, // Preserve favorite status
            releaseDate = apiMovie.releaseDate,

        )

    }

    movieDao.insertMovies(updatedMovies)
        return updatedMovies
}
    // Update the favorite status
    suspend fun toggleFavorite(movieId: Int, isFavorite: Boolean) {
        movieDao.updateFavoriteStatus(movieId, isFavorite)
    }
}