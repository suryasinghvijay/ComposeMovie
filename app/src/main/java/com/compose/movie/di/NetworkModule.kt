package com.compose.movie.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
//    @Provides @Singleton
//    fun getRunSheetApiService(
//        retrofit: Retrofit,
//    ): ApiServiceModule = retrofit.create(ApiServiceModule::class.java)
}
