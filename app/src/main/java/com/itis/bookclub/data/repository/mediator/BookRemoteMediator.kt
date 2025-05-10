package com.itis.bookclub.data.repository.mediator

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.itis.bookclub.R
import com.itis.bookclub.data.api.BookApi
import com.itis.bookclub.data.local.dao.BookDao
import com.itis.bookclub.data.local.dao.SearchQueryCacheDao
import com.itis.bookclub.data.local.entity.BookEntity
import com.itis.bookclub.data.local.entity.SearchQueryCacheEntity
import com.itis.bookclub.data.util.toEntity
import com.itis.bookclub.util.Constants
import com.itis.bookclub.util.Toaster

@OptIn(ExperimentalPagingApi::class)
class BookRemoteMediator(
    private val query: String,
    private val bookApi: BookApi,
    private val bookDao: BookDao,
    private val cacheDao: SearchQueryCacheDao,
    private val toaster: Toaster,
) : RemoteMediator<Int, BookEntity>() {

    private var didNotify = false

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, BookEntity>
    ): MediatorResult {
        if (loadType != LoadType.REFRESH) return MediatorResult.Success(true)

        val now = System.currentTimeMillis()
        val cache = cacheDao.getCache(query)
        val validUntil = cache?.lastRequestedAt?.plus(Constants.CACHE_MINUTES) ?: 0
        val shouldUseCache = if (cache != null) {
            val isFresh = now <= validUntil
            val queriesAfter = cacheDao.countQueriesAfter(cache.index)
            isFresh && queriesAfter <= 2
        } else false

        if (shouldUseCache) {
            if (!didNotify) {
                toaster.showFromCache()
                didNotify = true
            }
            return MediatorResult.Success(endOfPaginationReached = false)
        }

        val nextIndex = (cacheDao.getLatestIndex() ?: 0) + 1
        cacheDao.upsert(SearchQueryCacheEntity(query, now, nextIndex))

        return try {
            val page = 1
            val response = bookApi.getBooks(query, page, Constants.PAGE_LIMIT)
            val books = response?.bookList.orEmpty().mapNotNull { it.toEntity(query) }

            bookDao.clearByQuery(query)

            bookDao.insertAll(books)

            if (!didNotify) {
                toaster.showFromApi()
                didNotify = true
            }

            MediatorResult.Success(endOfPaginationReached = books.isEmpty())
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }
}

