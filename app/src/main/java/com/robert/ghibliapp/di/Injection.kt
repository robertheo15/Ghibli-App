package com.robert.ghibliapp.di

import com.robert.ghibliapp.data.FilmRepository

object Injection {
    fun provideRepository(): FilmRepository {
        return FilmRepository.getInstance()
    }
}
