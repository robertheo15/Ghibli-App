package com.robert.ghibliapp.ui.screen.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.robert.ghibliapp.di.Injection
import com.robert.ghibliapp.ui.ViewModelFactory
import com.robert.ghibliapp.ui.common.UiState
import com.robert.ghibliapp.ui.navigation.MovieDetailsTopBar
import com.robert.ghibliapp.ui.theme.GhibliAppTheme

@Composable
fun DetailScreen(
    filmId: Long,
    viewModel: DetailViewModel = viewModel(
        factory = ViewModelFactory(
            Injection.provideRepository()
        )
    ),
    navigateBack: () -> Unit,
    upPressed: () -> Unit
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getFilmById(filmId)
            }
            is UiState.Success -> {
                val data = uiState.data
                Scaffold(
                    backgroundColor = MaterialTheme.colors.background,
                    topBar = {
                        MovieDetailsTopBar(
                            title = data.film.title,
                            isFavorite = data.isFavoriteFilm,
                            onFavoriteClicked = {
//                                viewModel.onFavoriteClicked(
//                                    FilmEntity(
//                                        id = state.filmDetail?.id.toString(),
//                                        title = state.filmDetail?.title.toString(),
//                                        releaseDate = state.filmDetail?.releaseDate.toString(),
//                                        image = state.filmDetail?.image.toString()
//                                    )
//                                )
                            },
                            upPressed = navigateBack
                        )
                    }
                ) { padding ->
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(padding)
                    ) {
                        item {
                        }
                    }
                    DetailContent(
                        image = data.film.image,
                        movieBanner = data.film.movieBanner,
                        title = data.film.title,
                        originalTitle = data.film.originalTitle,
                        releaseDate = data.film.releaseDate,
                        runningTime = data.film.runningTime,
                        director = data.film.director,
                        producer = data.film.producer,
                        description = data.film.description,
                        onAddToFavorite = { isFavorite ->
                            viewModel.updateFavoriteFilm(data.film, isFavorite)
                            upPressed()
                        }
                    )
                }
            }
            is UiState.Error -> {}
        }
    }
}

@Composable
fun DetailContent(
    image: String,
    movieBanner: String,
    director: String,
    producer: String,
    title: String,
    originalTitle: String,
    releaseDate: String,
    runningTime: String,
    description: String,
    onAddToFavorite: (isFavorite: Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
//    var totalPoint by rememberSaveable { mutableStateOf(0) }
//    var orderCount by rememberSaveable { mutableStateOf(count) }

    Column(modifier = modifier) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .weight(1f)
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                Column(
                    Modifier
                        .fillMaxSize()
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(16 / 9f)
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(
                                        Color.Transparent,
                                        Color.Black
                                    ),
                                    startY = 100f

                                )
                            ),
                        contentAlignment = Alignment.Center

                    ) {
                        Image(
                            painter = rememberAsyncImagePainter(model = movieBanner),
                            contentDescription = title,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.matchParentSize()
                        )
                    }
                    Row(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth()
                            .offset(y = (-100).dp),
                        verticalAlignment = Alignment.Bottom
                    ) {
                        Image(
                            painter = rememberAsyncImagePainter(model = image),
                            contentDescription = title,
                            modifier = Modifier
                                .aspectRatio(2 / 3f)
                                .weight(2f)
                                .shadow(20.dp)
                                .clip(RoundedCornerShape(5.dp))
                        )
                        MovieInfo(
                            title = title,
                            originalTitle = originalTitle,
                            releaseDate = releaseDate,
                            runningTime = runningTime,
                            modifier = Modifier
                                .padding(10.dp, 0.dp)
                                .weight(4f)
                        )
                    }
                    Text(
                        text = "Produced by ${producer}\nDirected by ${director}\n\n${description}",
                        style = MaterialTheme.typography.subtitle1,
                        modifier = Modifier
                            .padding(16.dp)
                            .offset(y = (-100).dp),
                        textAlign = TextAlign.Justify
                    )
                }
            }
        }
    }
}

@Composable
private fun MovieInfo(
    title: String,
    originalTitle: String,
    releaseDate: String,
    runningTime: String,
    modifier: Modifier = Modifier
) {
    Column(modifier.fillMaxWidth()) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = title,
            style = MaterialTheme.typography.h5
        )
        Text(
            text = originalTitle,
            style = MaterialTheme.typography.h6
        )
        Text(
            text = "Released on $releaseDate • $runningTime Minutes",
            fontSize = 12.sp,
            fontStyle = FontStyle.Italic
        )
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_4)
@Composable
fun DetailContentPreview() {
    GhibliAppTheme {
        DetailContent(
            title = "The Tale of the Princess Kaguya",
            releaseDate = "2013",
            image = "https://image.tmdb.org/t/p/w600_and_h900_bestv2/mWRQNlWXYYfd2z4FRm99MsgHgiA.jpg",
            movieBanner = "https://image.tmdb.org/t/p/original/lMaWlYThCSnsmW3usxWTpSuyZp1.jpg",
            originalTitle = "かぐや姫の物語",
            producer = "Yoshiaki Nishimura",
            director = "Isao Takahata",
            description = "A bamboo cutter named Sanuki no Miyatsuko discovers a miniature girl inside a glowing bamboo shoot. Believing her to be a divine presence, he and his wife decide to raise her as their own, calling her 'Princess'.",
            runningTime = "137",
            onAddToFavorite = {}
        )
    }
}
