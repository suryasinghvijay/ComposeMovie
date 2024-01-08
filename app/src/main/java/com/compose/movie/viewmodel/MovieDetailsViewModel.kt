package com.compose.movie.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.compose.movie.model.MovieDetails
import com.compose.movie.repository.HomeScreenRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel class MovieDetailsViewModel @Inject constructor(private val repository: HomeScreenRepository) :
    ViewModel() {
    private val coroutineContext = Job() + Dispatchers.IO
    private val ioScope = CoroutineScope(coroutineContext)

    private val _isLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _movieDetailsResponse: MutableStateFlow<MovieDetails?> = MutableStateFlow(null)
    val movieDetailsResponse: StateFlow<MovieDetails?> = _movieDetailsResponse
    fun fetchMovieDetails(movieId: String) {
        viewModelScope.launch {
            repository.fetchMovieDetails(movieId).onStart {
                _isLoading.value = true
            }.onEach {
                _movieDetailsResponse.value = it
            }.catch {
                Log.e("TAG", "Thread is ${Thread.currentThread()}")
            }.onCompletion {
                _isLoading.value = false
            }.launchIn(ioScope)
        }
    }
}
