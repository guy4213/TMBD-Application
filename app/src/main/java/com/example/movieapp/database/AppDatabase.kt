package com.example.movieapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.movieapp.dao.MovieDao
import com.example.movieapp.entity.Movie


const val DB_VERSION =5
const val DB_NAME = "AppDatabase"
//database that gets its data with the help of entity - Movie
@Database(entities = [Movie::class], version = DB_VERSION)
abstract class AppDatabase : RoomDatabase() {
    //method for getting the db
    abstract fun movieDao(): MovieDao
    //creating the db,and recreating it to older version if the new one has not been found
    companion object {
        fun create(context: Context) = Room
            .databaseBuilder(context, AppDatabase::class.java, DB_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }
}
//CREATE DATABASE AppDatabase;