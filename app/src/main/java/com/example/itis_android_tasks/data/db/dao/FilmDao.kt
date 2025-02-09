package com.example.itis_android_tasks.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.itis_android_tasks.data.db.entities.FilmEntity

@Dao
interface FilmDao {
    @Insert(onConflict= OnConflictStrategy.ABORT)
    fun add(film : FilmEntity)

    @Query("SELECT * FROM films")
    fun getAll() : List<FilmEntity>

    @Update
    fun update(film : FilmEntity)

    @Delete
    fun delete(film : FilmEntity)

    @Query("SELECT * FROM films WHERE filmId = :id")
    fun get(id: Int) : FilmEntity

    @Query("SELECT * FROM films WHERE name = :name")
    suspend fun getByName(name : String): FilmEntity

    @Query("DELETE FROM films WHERE name = :name")
    suspend fun deleteByName(name : String)

    @Query("SELECT * FROM films ORDER BY year DESC")
    suspend fun getAllByYearDesc(): List<FilmEntity>?

    @Query("SELECT EXISTS (SELECT 1 FROM films WHERE name = :name LIMIT 1)")
    fun checkNameExists(name: String) : Boolean
}