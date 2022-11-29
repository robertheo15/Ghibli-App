package com.robert.ghibliapp.model

data class Film(
    val id: Long,
    val title: String,
    val originalTitle: String,
    val director: String,
    val description: String,
    val image: String,
    val movieBanner: String,
    val rtScore: String,
    val releaseDate: String,
    val producer: String,
    val runningTime: String,
)
