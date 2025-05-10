package com.itis.bookclub.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.itis.bookclub.data.api.BookApi
import com.itis.bookclub.data.local.dao.BookDao
import com.itis.bookclub.data.local.dao.SearchQueryCacheDao
import com.itis.bookclub.data.repository.mediator.BookRemoteMediator
import com.itis.bookclub.data.util.toDomainModel
import com.itis.bookclub.domain.model.BookDetailsDomainModel
import com.itis.bookclub.domain.model.BookDomainModel
import com.itis.bookclub.domain.repository.BookRepository
import com.itis.bookclub.util.Constants
import com.itis.bookclub.util.Toaster
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class BookRepositoryImpl @Inject constructor(
    private val bookApi: BookApi,
    private val bookDao: BookDao,
    private val cacheDao: SearchQueryCacheDao,
    private val toaster: Toaster,
) : BookRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun getBooks(query: String): Flow<PagingData<BookDomainModel>> {
        val pagingSourceFactory = { bookDao.pagingSource() }

        val shouldNotifyUser = true

        return Pager(
            config = PagingConfig(pageSize = Constants.PAGE_LIMIT),
            remoteMediator = BookRemoteMediator(
                query = query,
                bookApi = bookApi,
                bookDao = bookDao,
                cacheDao = cacheDao,
                toaster = toaster,
                shouldNotify = shouldNotifyUser,
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow.map { pagingData ->
            pagingData.map { it.toDomainModel() }
        }
    }


    override suspend fun getBookById(id: String): BookDetailsDomainModel =
        requireNotNull(bookApi.getBookById(id)).toDomainModel()
}