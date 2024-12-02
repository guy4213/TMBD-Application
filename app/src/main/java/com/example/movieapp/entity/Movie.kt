package com.example.movieapp.entity
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
@Entity
@Parcelize
data class Movie(
    @PrimaryKey
    val id: Int,
    val title: String,
    val overView:String,
    val posterUrl: String,
    val rating: Double,
    var isFavorite: Boolean = false, // New field to track favorite status
    val releaseDate: String,

):Parcelable