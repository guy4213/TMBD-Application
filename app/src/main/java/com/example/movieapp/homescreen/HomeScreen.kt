package com.example.movieapp.homescreen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.movieapp.entity.Movie
import com.example.movieapp.isMovieCurrentlyAiring
import com.example.movieapp.viewmodel.MovieViewModel
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen(
    movieViewModel: MovieViewModel, // ViewModel managing movie data and state
    onMovieClick: (Movie) -> Unit, // Callback for movie item click
    onFavoriteClick: (Movie) -> Unit, // Callback for toggling favorite status
    filterOption: MutableState<FilterOption>, // Current filter option state
    onFilterChange: (FilterOption) -> Unit, // Callback for changing the filter
    selectedMovieIndex: Int, // Index of the currently focused movie
    onMovieFocusChanged: (Int) -> Unit // Callback to update focused movie
) {
    val movies = movieViewModel.movies.collectAsState().value
    if (movies.isEmpty()) {
        // Refresh movies if the list is empty
        LaunchedEffect(Unit) { movieViewModel.refreshMovies() }
    }

    val listState = rememberLazyListState() // State for scrolling the list
    val expanded = remember { mutableStateOf(false) } // State for dropdown menu
    val coroutineScope = rememberCoroutineScope()

    // Filter movies based on the selected filter option
    val filteredMovies = when (filterOption.value) {
        FilterOption.POPULAR -> movies
        FilterOption.FAVORITES -> movies.filter { it.isFavorite }
        FilterOption.CURRENTLY_BROADCASTED -> movies.filter { isMovieCurrentlyAiring(it.releaseDate) }
    }

    // Reset focus to the first item if the selected index is invalid
    LaunchedEffect(filteredMovies) {
        if (selectedMovieIndex !in filteredMovies.indices) {
            onMovieFocusChanged(0)
        }
    }

    // Scroll to the selected movie index whenever it changes
    LaunchedEffect(selectedMovieIndex, filteredMovies) {
        if (selectedMovieIndex in filteredMovies.indices) {
            coroutineScope.launch {
                listState.animateScrollToItem(selectedMovieIndex)
            }
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        // Dropdown menu for selecting filter options
        FilterOptionsDropdown(
            filterOption = filterOption.value,
            onFilterChange = onFilterChange,
            expanded = expanded
        )

        Spacer(modifier = Modifier.height(8.dp)) // Space between dropdown and movie list

        // LazyColumn to display the list of movies
        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxSize()
                .onKeyEvent { event -> // Handle keyboard navigation
                    when (event.key) {
                        Key.DirectionDown -> {
                            val nextIndex = (selectedMovieIndex + 1).coerceAtMost(filteredMovies.size - 1)
                            onMovieFocusChanged(nextIndex)
                            true
                        }
                        Key.DirectionUp -> {
                            val previousIndex = (selectedMovieIndex - 1).coerceAtLeast(0)
                            onMovieFocusChanged(previousIndex)
                            true
                        }
                        Key.Enter -> {
                            if (selectedMovieIndex in filteredMovies.indices) {
                                onMovieClick(filteredMovies[selectedMovieIndex])
                            }
                            true
                        }
                        else -> false
                    }
                }
                .focusable() // Make the list focusable for navigation
        ) {
            itemsIndexed(filteredMovies) { index, movie ->
                // Display individual movie items
                MovieItem(
                    movie = movie,
                    isFocused = selectedMovieIndex == index,
                    onMovieClick = onMovieClick,
                    onFavoriteClick = onFavoriteClick,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
fun FilterOptionsDropdown(
    filterOption: FilterOption, // Current filter option
    onFilterChange: (FilterOption) -> Unit, // Callback for filter selection
    expanded: MutableState<Boolean> // Dropdown menu state
) {
    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.TopEnd) {
        // Icon to toggle dropdown menu visibility
        Icon(
            imageVector = Icons.Default.MoreVert,
            contentDescription = "More Options",
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(16.dp)
                .clickable { expanded.value = !expanded.value }
        )

        // Dropdown menu items for selecting a filter option
        DropdownMenu(
            expanded = expanded.value,
            onDismissRequest = { expanded.value = false }
        ) {
            DropdownMenuItem(onClick = {
                onFilterChange(FilterOption.POPULAR)
                expanded.value = false
            }) {
                Text("Popular")
            }
            DropdownMenuItem(onClick = {
                onFilterChange(FilterOption.FAVORITES)
                expanded.value = false
            }) {
                Text("Favorites")
            }
            DropdownMenuItem(onClick = {
                onFilterChange(FilterOption.CURRENTLY_BROADCASTED)
                expanded.value = false
            }) {
                Text("Currently Airing")
            }
        }
    }
}

enum class FilterOption {
    POPULAR, // Filter for popular movies
    CURRENTLY_BROADCASTED, // Filter for currently airing movies
    FAVORITES // Filter for favorite movies
}

@Composable
fun MovieItem(
    movie: Movie, // Movie data
    isFocused: Boolean, // Whether the movie is currently focused
    onMovieClick: (Movie) -> Unit, // Callback for movie click
    onFavoriteClick: (Movie) -> Unit, // Callback for toggling favorite status
    modifier: Modifier = Modifier
) {
    // Box to display each movie item with focus and click effects
    Box(
        modifier = modifier
            .padding(8.dp)
            .clip(RoundedCornerShape(8.dp))
            .then(
                if (isFocused) Modifier.border(2.dp, Color.Blue) else Modifier
            )
            .background(if (isFocused) Color.LightGray else Color.Transparent)
            .clickable { onMovieClick(movie) }
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            // Movie poster image
            Image(
                painter = rememberAsyncImagePainter(movie.posterUrl),
                contentDescription = "Movie Poster",
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
            Spacer(modifier = Modifier.height(8.dp)) // Space between poster and title
            Text(
                text = movie.title,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(16.dp, 0.dp)
            )
        }
        // Icon for toggling favorite status
        Icon(
            imageVector = if (movie.isFavorite) Icons.Filled.Star else Icons.Default.Star,
            contentDescription = "Favorite",
            tint = if (movie.isFavorite) Color.Green else Color.Gray,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp)
                .clickable { onFavoriteClick(movie) }
        )
    }
}