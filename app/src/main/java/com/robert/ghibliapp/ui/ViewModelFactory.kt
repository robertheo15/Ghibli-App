package com.robert.ghibliapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.robert.ghibliapp.data.FilmRepository
import com.robert.ghibliapp.ui.screen.detail.DetailViewModel
import com.robert.ghibliapp.ui.screen.favorite.FavoriteViewModel
import com.robert.ghibliapp.ui.screen.home.HomeViewModel

class ViewModelFactory(private val repository: FilmRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            return FavoriteViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}
