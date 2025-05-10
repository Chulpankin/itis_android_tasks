package com.itis.bookclub.presentation.utils

import com.itis.bookclub.domain.model.BookDetailsDomainModel
import com.itis.bookclub.domain.model.BookDomainModel
import com.itis.bookclub.presentation.model.BookDetailsUiModel
import com.itis.bookclub.presentation.model.BookUiModel

fun BookDomainModel.toUiModel() =
    BookUiModel(
        id = bookId,
        title = title,
        authorNames = authorNameList.joinToString(),
        publishedYear = publishedYear.toString(),
        coverUrl = coverUrl
    )

fun BookDetailsDomainModel.toUiModel() =
    BookDetailsUiModel(
        title = title,
        coverUrl = coverUrl,
        description = description
    )