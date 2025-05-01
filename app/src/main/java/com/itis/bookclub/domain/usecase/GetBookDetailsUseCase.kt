package com.itis.bookclub.domain.usecase

import com.itis.bookclub.domain.model.BookDetailsDomainModel
import com.itis.bookclub.domain.repository.BookRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetBookDetailsUseCase @Inject constructor(
    private val bookRepository: BookRepository,
    private val dispatcher: CoroutineDispatcher,
) {
    suspend operator fun invoke(id: String) : BookDetailsDomainModel {
        return withContext(dispatcher) {
            bookRepository.getBookById(id = id)
        }
    }
}