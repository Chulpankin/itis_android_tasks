package com.itis.bookclub.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.itis.bookclub.data.local.entity.BookEntity

@Dao
interface BookDao {
    @Query("SELECT * FROM books")
    fun pagingSource(): PagingSource<Int, BookEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(books: List<BookEntity>)

    @Query("DELETE FROM books")
    suspend fun clearAll()
}
