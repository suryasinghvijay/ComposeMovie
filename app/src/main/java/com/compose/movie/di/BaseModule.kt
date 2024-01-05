package com.compose.movie.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

private const val BASE_URL = "https://api.themoviedb.org/3/movie/"

@Module
@InstallIn(SingletonComponent::class)
object BaseModule {

    @Provides @Singleton
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides @Singleton
    fun provideConverter(gson: Gson): Converter.Factory = GsonConverterFactory.create(gson)

    @Provides @Singleton
    fun provideLogger() = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    @Provides @Singleton
    fun provideHeaderInterceptor() = HeaderNetworkInterceptor()

    @Provides fun provideOkHttpClientBuilder(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        headerNetworkInterceptor: HeaderNetworkInterceptor,
    ): OkHttpClient {
        return OkHttpClient.Builder().apply {
            connectTimeout(60, TimeUnit.SECONDS)
            readTimeout(20, TimeUnit.SECONDS)
            writeTimeout(20, TimeUnit.SECONDS).addInterceptor(httpLoggingInterceptor)
                .addNetworkInterceptor(headerNetworkInterceptor)
        }.build()
    }

    @Provides @Singleton
    fun provideRetrofitBuilder(): Retrofit.Builder = Retrofit.Builder()

    @Provides @Singleton
    fun providePreAccessTokenRetrofit(
        retrofitBuilder: Retrofit.Builder,
        okHttpClient: OkHttpClient,
        gson: Gson,
    ): Retrofit = retrofitBuilder.apply {
        addConverterFactory(GsonConverterFactory.create(gson))
        baseUrl(BASE_URL)
        client(okHttpClient)
    }.build()

    @Provides @Singleton
    fun getRunSheetApiService(
        retrofit: Retrofit,
    ): ApiServiceModule = retrofit.create(ApiServiceModule::class.java)
}
