package com.example.movieapp.mapper

import com.example.movieapp.entity.Movie
import com.example.movieapp.service.responsedto.MovieDto
const val baseUrl = "https://image.tmdb.org/t/p/w500"
fun MovieDto.toMovie(): Movie {
    return Movie(
        id = this.id,
        title = this.title,
        overView = this.overview,
        posterUrl=baseUrl+this.poster_path,
        rating = this.vote_average,
        releaseDate=this.release_date,
   )
}