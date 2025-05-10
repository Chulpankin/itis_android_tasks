package com.itis.bookclub.domain.repository

import com.itis.bookclub.domain.model.BookDetailsDomainModel
import com.itis.bookclub.domain.model.BookDomainModel

interface BookRepository {
    suspend fun getBooks(
        query: String, page: Int, limit: Int
    ) : List<BookDomainModel>

    suspend fun getBookById(id: String) : BookDetailsDomainModel
}