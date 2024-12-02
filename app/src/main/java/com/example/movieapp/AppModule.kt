package com.example.movieapp

import android.content.Context
import androidx.room.Room
import com.example.movieapp.database.AppDatabase
import com.example.movieapp.database.DB_NAME
import com.example.movieapp.service.TMDBApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    // This class provides dependencies to be used across the application.

    // Base URL for API calls. It is loaded from the BuildConfig configuration file.
    private val baseUrl: String = BuildConfig.BASE_URL

    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        // LoggingInterceptor logs HTTP request and response details for debugging purposes.
        val logger = HttpLoggingInterceptor()
        logger.level = HttpLoggingInterceptor.Level.BODY
        return logger
    }

    @Provides
    fun provideHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
    ): OkHttpClient {
        // Creates an OkHttpClient with the logging interceptor to manage HTTP calls.
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Provides
    fun provideMovieService(client: OkHttpClient): TMDBApiService {
        // Configures Retrofit for network requests to TMDB API, combining the base URL and HTTP client.
        return Retrofit.Builder()
            .client(client)
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TMDBApiService::class.java)
    }

    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context) = Room
        // Provides an instance of the database for local data storage.
        .databaseBuilder(context, AppDatabase::class.java, DB_NAME)
        .fallbackToDestructiveMigration() // Automatically rebuilds the database in case of schema changes.
        .build()

    @Provides
    fun provideMovieDao(db: AppDatabase) = db.movieDao()
    // Provides the DAO (Data Access Object) for database queries.
}


