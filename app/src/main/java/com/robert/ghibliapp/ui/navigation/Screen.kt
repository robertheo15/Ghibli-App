package com.robert.ghibliapp.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Profile : Screen("profile")
    object Favorite : Screen("favorite")
    object DetailFilm : Screen("home/{filmId}") {
        fun createRoute(filmId: Long) = "home/$filmId"
    }
}
