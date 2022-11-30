package com.robert.ghibliapp.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.robert.ghibliapp.data.FilmRepository
import com.robert.ghibliapp.model.FavoriteFilm
import com.robert.ghibliapp.model.Film
import com.robert.ghibliapp.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailViewModel(
    private val repository: FilmRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<FavoriteFilm>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<FavoriteFilm>>
        get() = _uiState

    fun getFilmById(filmId: Long) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            _uiState.value = UiState.Success(repository.getFilmById(filmId))
        }
    }

    fun updateFavoriteFilm(film: Film, isFavorite: Boolean) {
        viewModelScope.launch {
            repository.updateFavoriteFilm(film.id, !isFavorite)
        }
    }
}