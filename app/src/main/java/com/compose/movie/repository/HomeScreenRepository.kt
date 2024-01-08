package com.compose.movie.repository

import android.util.Log
import com.compose.movie.di.ApiServiceModule
import com.compose.movie.model.PopularMovieResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class HomeScreenRepository @Inject constructor(
    private val apiService: ApiServiceModule,
) {

    suspend fun fetchPopularMovies(): Flow<PopularMovieResponse> = flow {
        Log.e("TAG", "fetchPopularMovies repository")
        Log.e("TAG", "Thread is ${Thread.currentThread()}")
        val response = apiService.getPopularMovieList()
        Log.e("apiResponse", "$response")
        Log.e("TAG", "Thread is ${Thread.currentThread()}")
        emit(response)
    }

    suspend fun fetchTopRatedMovies(): Flow<PopularMovieResponse> = flow {
        val response = apiService.getTopRatedMovieList()
        emit(response)
    }

    suspend fun fetchMovieDetails(movieId: String) = flow {
        val response = apiService.getMovieDetails(movieId)
        emit(response)
    }
}
