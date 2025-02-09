package com.example.itis_android_tasks.data.model


data class FilmModel(
    val name : String,
    val year : Int,
    val description : String,
    var isFavorite: Boolean = false
)