package com.itis.bookclub.presentation.model

data class BookUiModel(
    val id: String,
    val authorNames: String,
    val title: String,
    val publishedYear: String,
    val coverUrl: String,
)