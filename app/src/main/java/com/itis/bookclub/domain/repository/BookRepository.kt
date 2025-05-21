package com.itis.bookclub.domain.repository

import androidx.paging.PagingData
import com.itis.bookclub.domain.model.BookDetailsDomainModel
import com.itis.bookclub.domain.model.BookDomainModel
import kotlinx.coroutines.flow.Flow

interface BookRepository {

    fun getBooks(query: String) : Flow<PagingData<BookDomainModel>>

    suspend fun getBookById(id: String) : BookDetailsDomainModel
}