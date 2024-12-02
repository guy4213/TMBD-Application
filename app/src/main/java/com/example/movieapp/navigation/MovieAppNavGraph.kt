package com.example.movieapp.navigation

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.movieapp.homescreen.FilterOption
import com.example.movieapp.homescreen.HomeScreen
import com.example.movieapp.moviedetails.MovieDetailsScreen
import com.example.movieapp.viewmodel.MovieViewModel
@SuppressLint("StateFlowValueCalledInComposition")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MovieAppNavGraph(viewModel: MovieViewModel) {
    val navController = rememberNavController()

    // Collecting movies state from the ViewModel (the movies list should be a StateFlow or LiveData)
    val movies = viewModel.movies.collectAsState().value

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            // Pass the movies directly from the ViewModel state
            val filterOption = remember { mutableStateOf(FilterOption.POPULAR) }
            val selectedMovieIndex = remember { mutableStateOf(0) }

            HomeScreen(
                movieViewModel = viewModel,
                onMovieClick = { movie ->
                    // Navigate to movie details screen
                    navController.navigate("details/${movie.id}")
                },
                onFavoriteClick = { movie ->
                    // Toggle the favorite status
                    viewModel.toggleFavorite(movie)
                },
                filterOption = filterOption, // Initially set to POPULAR
                onFilterChange = { newFilterOption ->
                    filterOption.value = newFilterOption // Update the filter state here
                },
                selectedMovieIndex = selectedMovieIndex.value,
                onMovieFocusChanged = { index ->
                    selectedMovieIndex.value = index // Update the selected index
                }
            )
        }

        composable(
            "details/{movieId}",
            arguments = listOf(navArgument("movieId") { type = NavType.IntType })
        ) { backStackEntry ->
            val movieId = backStackEntry.arguments?.getInt("movieId")
            // Find the movie using the movieId
            val movie = movies.find { it.id == movieId }
            movie?.let {
                MovieDetailsScreen(
                    movie = it,
                    onBack = {
                        // Explicitly clear the back stack or use a more controlled navigation
                        navController.popBackStack(route = "home", inclusive = false)
                    },
                    onFavoriteClick = { movie ->
                        // Toggle the favorite status
                        viewModel.toggleFavorite(movie) // Pass the entire movie object
                    }
                )
            }
        }
    }
}
