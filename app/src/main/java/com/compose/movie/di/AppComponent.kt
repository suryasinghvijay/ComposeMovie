package com.compose.movie.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module(
    includes = [
        BaseModule::class, NetworkModule::class,
    ],
)
@InstallIn(SingletonComponent::class)
object AppComponent
