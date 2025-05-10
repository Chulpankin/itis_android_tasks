package com.itis.bookclub.data.di

import android.content.Context
import androidx.room.Room
import com.itis.bookclub.data.local.AppDatabase
import com.itis.bookclub.data.local.dao.BookDao
import com.itis.bookclub.data.local.dao.SearchQueryCacheDao
import com.itis.bookclub.data.local.entity.SearchQueryCacheEntity
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class LocalModule {
    @Provides
    @Singleton
    fun provideAppDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "bookclub_database"
        ).build()
    }

    @Provides
    fun provideBookDao(appDatabase: AppDatabase): BookDao {
        return appDatabase.bookDao()
    }

    @Provides
    fun provideSearchQueryCacheDao(appDatabase: AppDatabase): SearchQueryCacheDao {
        return appDatabase.searchQueryCacheDao()
    }
}
