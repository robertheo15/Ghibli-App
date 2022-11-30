package com.robert.ghibliapp.data

import com.robert.ghibliapp.model.FakeFilmDataSource
import com.robert.ghibliapp.model.FavoriteFilm
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class FilmRepository {

    private val favoriteFilms = mutableListOf<FavoriteFilm>()

    init {
        if (favoriteFilms.isEmpty()) {
            FakeFilmDataSource.dummyFilms.forEach {
                favoriteFilms.add(FavoriteFilm(it, false))
            }
        }
    }

    fun getAllFilms(): Flow<List<FavoriteFilm>> {
        return flowOf(favoriteFilms)
    }

    fun getFavoriteFilms(): Flow<List<FavoriteFilm>> {
        return getAllFilms()
            .map { favoriteFilms ->
                favoriteFilms.filter { favoriteFilm ->
                    favoriteFilm.isFavoriteFilm
                }
            }
    }

    fun getFilmById(filmId: Long): FavoriteFilm {
        return favoriteFilms.first {
            it.film.id == filmId
        }
    }

    fun updateFavoriteFilm(rewardId: Long, isFavorite: Boolean): Flow<Boolean> {
        val index = favoriteFilms.indexOfFirst { it.film.id == rewardId }
        val result = if (index >= 0) {
            val favoriteFilm = favoriteFilms[index]
            favoriteFilms[index] =
                favoriteFilm.copy(film = favoriteFilm.film, isFavoriteFilm = isFavorite)
            true
        } else {
            false
        }
        return flowOf(result)
    }

    companion object {
        @Volatile
        private var instance: FilmRepository? = null

        fun getInstance(): FilmRepository =
            instance ?: synchronized(this) {
                FilmRepository().apply {
                    instance = this
                }
            }
    }
}
