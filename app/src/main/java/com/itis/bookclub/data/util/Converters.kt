package com.itis.bookclub.data.util

import BookDetailsResponse
import com.itis.bookclub.data.model.BookResponse
import com.itis.bookclub.domain.model.BookDetailsDomainModel
import com.itis.bookclub.domain.model.BookDomainModel

fun BookResponse.toDomainModel() =
    BookDomainModel(
        authorIdList = authorKeyList.map { requireNotNull(it) },
        bookId = requireNotNull(key),
        authorNameList = authorNameList.map { requireNotNull(it) },
        title = requireNotNull(title),
        publishedYear = firstPublishedYear,
        coverUrl = requireNotNull(coverId).toOpenLibraryCoverUrl(),
    )

fun BookDetailsResponse.toDomainModel() =
    BookDetailsDomainModel(
        bookId = requireNotNull(key),
        title = requireNotNull(title),
        coverUrl = covers[0].toString().toOpenLibraryCoverUrl(),
        description = requireNotNull(description?.value),
    )

fun String.toOpenLibraryCoverUrl() = "https://covers.openlibrary.org/b/id/$this.jpg"