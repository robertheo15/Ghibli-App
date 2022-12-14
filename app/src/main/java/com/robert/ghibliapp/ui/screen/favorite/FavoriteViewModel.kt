package com.robert.ghibliapp.ui.screen.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.robert.ghibliapp.data.FilmRepository
import com.robert.ghibliapp.model.FavoriteFilm
import com.robert.ghibliapp.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class FavoriteViewModel(
    private val repository: FilmRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<List<FavoriteFilm>>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<FavoriteFilm>>>
        get() = _uiState

    fun getFavoriteFilms() {
        viewModelScope.launch {
            repository.getFavoriteFilms()
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect { films ->
                    _uiState.value = UiState.Success(films)
                }
        }
    }
}
