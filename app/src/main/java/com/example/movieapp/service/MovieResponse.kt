package com.example.movieapp.service

import com.example.movieapp.service.responsedto.MovieDto
import com.google.gson.annotations.SerializedName

//gets us the Movie dto list
data class MovieResponse(
    @SerializedName("results")
    val movies: List<MovieDto>
)