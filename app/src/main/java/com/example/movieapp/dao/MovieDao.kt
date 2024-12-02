package com.example.movieapp.dao
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movieapp.entity.Movie

@Dao
interface MovieDao {
    @Query("SELECT * FROM Movie")
    fun getAllMoviesLiveData(): LiveData<List<Movie>> // Observe movies with LiveData

    @Query("SELECT * FROM Movie")
    suspend fun getAllMoviesSync(): List<Movie>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<Movie>)

    @Query("UPDATE Movie SET isFavorite = :isFavorite WHERE id = :movieId")
    suspend fun updateFavoriteStatus(movieId: Int, isFavorite: Boolean)
}