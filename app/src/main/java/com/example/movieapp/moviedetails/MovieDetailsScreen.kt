package com.example.movieapp.moviedetails

//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.movieapp.entity.Movie

@Composable
fun MovieDetailsScreen(
    movie: Movie,
    onBack: () -> Unit,
    onFavoriteClick: (Movie) -> Unit
) {
    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .focusable()
            .onKeyEvent { keyEvent ->
                when (keyEvent.key) {
                    Key.Back, Key.Escape -> {
                        onBack()
                        true
                    }
                    Key.Enter -> {
                        // Directly call onFavoriteClick with the movie
                        // This ensures the viewmodel or parent component handles the favorite state
                        onFavoriteClick(movie)
                        true
                    }
                    else -> false
                }
            }
            .padding(16.dp)
    ) {
        // Back button
        IconButton(onClick = { onBack() }) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
        }

        // Movie Poster
        Image(
            painter = rememberAsyncImagePainter(movie.posterUrl),
            contentDescription = "Movie Poster",
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .clip(RoundedCornerShape(8.dp))
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Title
        Text(
            text = movie.title,
            style = MaterialTheme.typography.h3,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(8.dp))
       //preview
        Text(
            text = movie.overView,
            style = MaterialTheme.typography.h5,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        // Favorite Icon
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Icon(
                imageVector = if (movie.isFavorite) Icons.Filled.Star else Icons.Default.Star,
                contentDescription = "Add to Favorites",
                tint = if (movie.isFavorite) Color.Green else Color.Gray,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .clickable {
                        onFavoriteClick(movie)
                    }
            )
        }
    }
}