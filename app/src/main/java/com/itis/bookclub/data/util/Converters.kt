package com.itis.bookclub.data.util

import BookDetailsResponse
import com.itis.bookclub.data.local.entity.BookEntity
import com.itis.bookclub.data.model.BookResponse
import com.itis.bookclub.domain.model.BookDetailsDomainModel
import com.itis.bookclub.domain.model.BookDomainModel

fun BookResponse.toDomainModel() =
    BookDomainModel(
        authorIdList = authorKeyList.map { requireNotNull(it) },
        bookId = requireNotNull(key?.split("/works")?.get(1)),
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

fun BookResponse.toEntity(): BookEntity? {
    val id = key?.removePrefix("/works/") ?: return null
    val titleSafe = title ?: return null
    val coverSafe = coverId?.toOpenLibraryCoverUrl() ?: ""
    val authorNames = authorNameList.filterNotNull().joinToString(", ")
    val authorIds = authorKeyList.filterNotNull().joinToString(", ")

    return BookEntity(
        bookId = id,
        title = titleSafe,
        authorNames = authorNames,
        authorIds = authorIds,
        publishedYear = firstPublishedYear,
        coverUrl = coverSafe
    )
}

fun BookEntity.toDomainModel() =
    BookDomainModel(
        authorIdList = authorIds.split(", ").filter { it.isNotBlank() },
        bookId = bookId,
        authorNameList = authorNames.split(", ").filter { it.isNotBlank() },
        title = title,
        publishedYear = publishedYear,
        coverUrl = coverUrl
    )


fun String.toOpenLibraryCoverUrl() = "https://covers.openlibrary.org/b/id/$this.jpg"