package com.itis.bookclub.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
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
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
