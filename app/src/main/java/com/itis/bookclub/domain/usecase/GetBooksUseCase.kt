package com.itis.bookclub.domain.usecase

import com.itis.bookclub.domain.model.BookDomainModel
import com.itis.bookclub.domain.repository.BookRepository
import com.itis.bookclub.util.Constants
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetBooksUseCase @Inject constructor(
    private val bookRepository: BookRepository,
    private val dispatcher: CoroutineDispatcher,
) {

    suspend operator fun invoke(query: String, page: Int) : List<BookDomainModel> {
        return withContext(dispatcher) {
            bookRepository.getBooks(
                query = query,
                page = page,
                limit = Constants.PAGE_LIMIT,
            )
        }
    }
}