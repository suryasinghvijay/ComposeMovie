package com.compose.movie.di

import com.compose.movie.model.PopularMovieResponse
import retrofit2.http.GET

interface ApiServiceModule {
    @GET("popular?language=en-US&page=1")
    suspend fun getPopularMovieList(): PopularMovieResponse

    @GET("top_rated?language=en-US&page=1")
    suspend fun getTopRatedMovieList(): PopularMovieResponse
}
