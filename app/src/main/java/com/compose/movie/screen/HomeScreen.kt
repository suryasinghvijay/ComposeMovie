package com.compose.movie.screen

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.compose.movie.R
import com.compose.movie.model.Result
import com.compose.movie.ui.theme.ComposeMovieTheme
import com.compose.movie.viewmodel.HomeScreenViewModel

const val POPULAR_MOVIE_POSTER_PATH = "https://www.themoviedb.org/t/p/w220_and_h330_face"

@Composable fun HomeScreen(modifier: Modifier = Modifier) {
    val movieDetailViewModel = hiltViewModel<HomeScreenViewModel>()

    LaunchedEffect(key1 = true) {
        movieDetailViewModel.fetchPopularMovies()
        movieDetailViewModel.fetchTopRatedMovieList()
    }
    Column {
        movieDetailViewModel.movies.collectAsState().value?.let {
            Log.e("TAG", "observerCalled $it ")
            PopularMovieList(it, title = "Trending")
        }
        movieDetailViewModel.topRatedMovies.collectAsState().value?.let {
            Log.e("TAG", "observerCalled $it ")
            PopularMovieList(it, title = "What's Popular")
        }
    }
    movieDetailViewModel.isLoading.collectAsState().value.apply {
        Log.e("TAG", "progress status $this")
        if (this) {
            CustomProgressIndicator()
        }
    }
}

@Composable fun PopularMovieList(results: List<Result>, modifier: Modifier = Modifier, title: String) {
    Column(modifier = modifier.padding(8.dp)) {
        Text(
            text = title,
            textAlign = TextAlign.Start,
            fontWeight = FontWeight.SemiBold,
            style = MaterialTheme.typography.titleLarge, // minLines = 2,
            maxLines = 2,
        )
        Spacer(modifier = modifier.height(12.dp))
        LazyRow {
            items(results) {
                MovieScreen(result = it)
                if (results.indexOf(it) < results.lastIndex) {
                    Spacer(modifier = modifier.width(12.dp))
                }
            }
        }
    }
}

@Composable fun MovieScreen(modifier: Modifier = Modifier, result: Result) {
    Box() {
        Column(
            modifier = modifier.wrapContentHeight().width(150.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current).data(POPULAR_MOVIE_POSTER_PATH.plus(result.poster_path))
                    .crossfade(true).build(),
                placeholder = painterResource(R.drawable.ic_launcher_foreground),
                contentDescription = "stringResource(R.string.description)",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.clip(RoundedCornerShape(8.dp)).width(150.dp).height(250.dp),
            )
            Text(
                text = result.original_title,
                modifier = modifier.wrapContentWidth().padding(start = 8.dp, end = 8.dp),
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold, // minLines = 2,
                maxLines = 2,
            )
            Text(
                text = result.release_date,
                modifier = modifier.wrapContentWidth().padding(start = 8.dp, end = 8.dp),
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Light,
            )
        }
    }
}

@Composable fun CustomProgressIndicator(modifier: Modifier = Modifier) { // Adjust colors, size, and stroke width
    Box(modifier = modifier.width(50.dp).height(50.dp).fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator(
            color = MaterialTheme.colorScheme.primary,
            strokeWidth = 4.dp, // Adjust size as needed
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ComposeMovieTheme {
        CircularProgressIndicator()
    }
}
