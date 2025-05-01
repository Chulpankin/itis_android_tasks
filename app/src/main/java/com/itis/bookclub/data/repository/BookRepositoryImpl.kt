package com.itis.bookclub.data.repository

import com.itis.bookclub.data.api.BookApi
import com.itis.bookclub.data.util.toDomainModel
import com.itis.bookclub.domain.model.BookDetailsDomainModel
import com.itis.bookclub.domain.model.BookDomainModel
import com.itis.bookclub.domain.repository.BookRepository
import javax.inject.Inject

class BookRepositoryImpl @Inject constructor(
    private val bookApi: BookApi
) : BookRepository {

    override suspend fun getBooks(query: String, page: Int, limit: Int): List<BookDomainModel> =
        requireNotNull(bookApi.getBooks(query, page, limit)?.bookList).map { it.toDomainModel() }

    override suspend fun getBookById(id: String): BookDetailsDomainModel =
        requireNotNull(bookApi.getBookById("$id.json")).toDomainModel()
}