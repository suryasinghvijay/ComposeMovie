package com.compose.movie.viewmodel

import android.util.Log
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.compose.movie.model.Result
import com.compose.movie.repository.HomeScreenRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel class HomeScreenViewModel @Inject constructor(private val repository: HomeScreenRepository) :
    ViewModel() {
    private val coroutineContext = Job() + Dispatchers.IO
    private val ioScope = CoroutineScope(coroutineContext)

    private val movieDetail: MutableStateFlow<List<Result>?> = MutableStateFlow(null)
    val movies: StateFlow<List<Result>?> = movieDetail

    private val topRatedMovieDetail: MutableStateFlow<List<Result>?> = MutableStateFlow(null)
    val topRatedMovies: StateFlow<List<Result>?> = topRatedMovieDetail

    private val _isLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun fetchPopularMovies() {
        viewModelScope.launch {
            repository.fetchPopularMovies().onStart {
                Log.e("TAG", "onStart happened")
                Log.e("TAG", "Thread is ${Thread.currentThread()}") // movieDetail.value = DataState.Loading
                _isLoading.value = true
            }.onEach {
                Log.e("TAG", "for each happened ${it.totalResults}")
                Log.e("TAG", "Thread is ${Thread.currentThread()}")
                movieDetail.value = it.results
            }.catch {
                Log.e("TAG", "error happened $it")
                Log.e("TAG", "Thread is ${Thread.currentThread()}") // movieDetail.value = DataState.Error(it)
            }.onCompletion {
                _isLoading.value = false
            }.launchIn(ioScope)
        }
    }

    fun fetchTopRatedMovieList() {
        viewModelScope.launch {
            repository.fetchTopRatedMovies().onStart {
                Log.e("TAG", "onStart happened")
                Log.e("TAG", "Thread is ${Thread.currentThread()}") // movieDetail.value = DataState.Loading
                _isLoading.value = true
            }.onEach {
                Log.e("TAG", "for each happened ${it.totalResults}")
                Log.e("TAG", "Thread is ${Thread.currentThread()}")
                topRatedMovieDetail.value = it.results
            }.catch {
                Log.e("TAG", "error happened $it")
                Log.e("TAG", "Thread is ${Thread.currentThread()}") // movieDetail.value = DataState.Error(it)
            }.onCompletion {
                _isLoading.value = false
            }.launchIn(ioScope)
        }
    }

    override fun onCleared() {
        ioScope.cancel()
        super.onCleared()
    }
}
