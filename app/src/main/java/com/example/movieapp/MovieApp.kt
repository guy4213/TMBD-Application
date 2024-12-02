package com.example.movieapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MovieApp : Application() {
    // This is the application class, annotated with @HiltAndroidApp, which initializes Hilt.
}
