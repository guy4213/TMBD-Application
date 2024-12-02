package com.example.movieapp

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
fun isMovieCurrentlyAiring(releaseDate: String): Boolean {
    // Determines whether a movie is currently airing based on its release date.
    val now = LocalDate.now() // Gets the current date.
    val movieReleaseDate = LocalDate.parse(releaseDate) // Parses the release date provided by the API.

    // Returns true if the movie release date is today or in the past.
    return !movieReleaseDate.isAfter(now)
}