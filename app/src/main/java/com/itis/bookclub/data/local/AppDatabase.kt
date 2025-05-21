package com.itis.bookclub.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.itis.bookclub.data.local.dao.BookDao
import com.itis.bookclub.data.local.dao.SearchQueryCacheDao
import com.itis.bookclub.data.local.entity.BookEntity
import com.itis.bookclub.data.local.entity.SearchQueryCacheEntity

@Database(
    entities = [BookEntity::class, SearchQueryCacheEntity::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun bookDao(): BookDao
    abstract fun searchQueryCacheDao(): SearchQueryCacheDao
}
