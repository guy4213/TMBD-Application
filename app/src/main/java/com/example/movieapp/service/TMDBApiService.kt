package com.example.movieapp.service

import retrofit2.http.GET
import retrofit2.http.Query

interface TMDBApiService {
    @GET("movie")
    suspend fun getPopularMovies(
        @Query("api_key") api_key:String,
        @Query("page") page: Int
    ): MovieResponse
}

//https://api.themoviedb.org/3/discover/movie?api_key=8a9f0413580a212aafb92309ff97da4d