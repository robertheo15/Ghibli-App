package com.robert.ghibliapp.ui.screen.favorite

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.robert.ghibliapp.di.Injection
import com.robert.ghibliapp.model.FavoriteFilm
import com.robert.ghibliapp.ui.ViewModelFactory
import com.robert.ghibliapp.ui.common.UiState
import com.robert.ghibliapp.ui.components.MovieCard

@Composable
fun FavoriteScreen(
    modifier: Modifier = Modifier,
    viewModel: FavoriteViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
    navigateToDetail: (Long) -> Unit
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getFavoriteFilms()
            }
            is UiState.Success -> {
                if (uiState.data.isEmpty()) {
                    val composition by rememberLottieComposition(
                        spec = LottieCompositionSpec.Url("https://assets3.lottiefiles.com/packages/lf20_hl5n0bwb.json")
                    )
                    LottieAnimation(
                        composition = composition,
                        iterations = LottieConstants.IterateForever
                    )
                }
                FavoriteContent(
                    favoriteFilm = uiState.data,
                    modifier = modifier.testTag("FilmList"),
                    navigateToDetail = navigateToDetail
                )
            }
            is UiState.Error -> {}
        }
    }
}

@Composable
fun FavoriteContent(
    favoriteFilm: List<FavoriteFilm>,
    modifier: Modifier = Modifier,
    navigateToDetail: (Long) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(160.dp),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier.testTag("FilmList")
    ) {
        items(favoriteFilm) { data ->
            MovieCard(
                image = data.film.image,
                title = data.film.title,
                releaseDate = data.film.releaseDate,
                modifier = Modifier.clickable {
                    navigateToDetail(data.film.id)
                }
            )
        }
    }
}
