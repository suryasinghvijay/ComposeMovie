package com.compose.movie.screen

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.compose.movie.R
import com.compose.movie.model.MovieDetails
import com.compose.movie.viewmodel.MovieDetailsViewModel

const val BACKGROUND_IMAGE = "https://www.themoviedb.org/t/p/w1920_and_h800_multi_faces"
const val POSTER_IMAGE = "https://www.themoviedb.org/t/p/w440_and_h660_face"
const val BLURED_IMAGE = "https://www.themoviedb.org/t/p/w300_and_h450_bestv2_filter(blur)"

@Composable fun MovieDetailsScreen(modifier: Modifier = Modifier) {
    val movieDetailViewModel = hiltViewModel<MovieDetailsViewModel>()

    LaunchedEffect(true) {
        movieDetailViewModel.fetchMovieDetails("906126")
    }

    movieDetailViewModel.movieDetailsResponse.collectAsState().value?.let {
        Log.e("TAG", "observerCalled $it ")
        Column {
            MovieDetailsHeader(modifier, it)
            MovieItemDetails(modifier, it)
        }
    }
}

@Composable fun MovieItemDetails(modifier: Modifier, movieDetails: MovieDetails) {
    Column() { //
        Spacer(modifier = modifier.height(12.dp))
        Text(
            text = movieDetails.title,
            modifier = modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Normal,
            fontSize = 20.sp,
            color = Color.White,
        )
        Spacer(modifier = modifier.height(18.dp))
        Box(modifier = modifier.wrapContentHeight()) {
            Column {
                Text(
                    text = movieDetails.releaseDate,
                    modifier = modifier.fillMaxWidth().padding(8.dp),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Normal,
                    fontStyle = FontStyle.Italic,
                    fontSize = 20.sp,
                    color = Color.White,
                )
                Text(
                    text = movieDetails.genres.map { it.name }.toMutableList().joinToString(","),
                    modifier = modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Normal,
                    fontStyle = FontStyle.Italic,
                    fontSize = 20.sp,
                    color = Color.White,
                )
            }
        }
        Spacer(modifier = modifier.height(18.dp))
        Text(
            text = movieDetails.tagline,
            modifier = modifier.fillMaxWidth(),
            textAlign = TextAlign.Start,
            fontWeight = FontWeight.Normal,
            fontStyle = FontStyle.Italic,
            fontSize = 20.sp,
            color = Color.White,
        )
        Spacer(modifier = modifier.height(12.dp))
        Text(
            text = "OverView",
            modifier = modifier.fillMaxWidth(),
            textAlign = TextAlign.Start,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            color = Color.White,
        )
        Spacer(modifier = modifier.height(4.dp))
        Text(
            text = movieDetails.overview,
            modifier = modifier.fillMaxWidth(),
            textAlign = TextAlign.Start,
            fontWeight = FontWeight.Normal,
            color = Color.White,
        )
    }
}

@Composable fun MovieDetailsHeader(modifier: Modifier = Modifier, movieDetails: MovieDetails) {
    Box { //
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current).data(BLURED_IMAGE.plus(movieDetails.posterPath))
                .crossfade(true).build(),
            placeholder = painterResource(R.drawable.ic_launcher_foreground),
            contentDescription = "stringResource(R.string.description)",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxWidth().height(220.dp),
        )
        Box(modifier = modifier.align(Alignment.CenterStart)) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current).data(BACKGROUND_IMAGE.plus(movieDetails.backdropPath))
                    .crossfade(true).build(),
                placeholder = painterResource(R.drawable.ic_launcher_foreground),
                contentDescription = "stringResource(R.string.description)",
                contentScale = ContentScale.Fit,
                modifier = Modifier.fillMaxWidth().height(200.dp).padding(start = 80.dp),
            )

            Box(modifier = modifier.align(Alignment.CenterStart)) { //
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current).data(POSTER_IMAGE.plus(movieDetails.posterPath))
                        .crossfade(true).build(),
                    placeholder = painterResource(R.drawable.ic_launcher_foreground),
                    contentDescription = "stringResource(R.string.description)",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.clip(RoundedCornerShape(16.dp)).width(150.dp).height(200.dp).padding(8.dp),
                )
            }
        }
    }
}

@Composable @Preview
fun MovieDetailsPreview() {
    MovieDetailsScreen()
}
