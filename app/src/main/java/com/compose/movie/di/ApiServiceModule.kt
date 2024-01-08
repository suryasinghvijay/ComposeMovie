package com.compose.movie.di

import com.compose.movie.model.MovieDetails
import com.compose.movie.model.PopularMovieResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiServiceModule {
    @GET("popular?language=en-US&page=1")
    suspend fun getPopularMovieList(): PopularMovieResponse

    @GET("top_rated?language=en-US&page=1")
    suspend fun getTopRatedMovieList(): PopularMovieResponse

    @GET("{movieId}?language=en-US")
    suspend fun getMovieDetails(@Path("movieId") movieId: String): MovieDetails
}
