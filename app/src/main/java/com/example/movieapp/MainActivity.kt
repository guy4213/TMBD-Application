package com.example.movieapp

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import com.example.movieapp.navigation.MovieAppNavGraph
import com.example.movieapp.viewmodel.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    // Injects the MovieViewModel using Hilt for dependency management.
    private val movieViewModel: MovieViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Initializes the navigation graph and passes the MovieViewModel for managing app state.
            MovieAppNavGraph(viewModel = movieViewModel)
        }
    }
}