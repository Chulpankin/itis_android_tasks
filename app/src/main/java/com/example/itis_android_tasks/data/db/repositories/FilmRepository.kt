package com.example.itis_android_tasks.data.db.repositories

import com.example.itis_android_tasks.data.db.dao.FilmDao
import com.example.itis_android_tasks.data.db.entities.FilmEntity
import com.example.itis_android_tasks.data.model.FilmModel
import com.example.itis_android_tasks.di.ServiceLocator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object FilmRepository {

    private val filmDao : FilmDao = ServiceLocator.getDbInstance().getFilmDao()

    suspend fun get(id : Int) : FilmEntity {
        return withContext(Dispatchers.IO) {
            return@withContext filmDao.get(id)
        }
    }

    suspend fun add(film : FilmModel) : Boolean {
        return withContext(Dispatchers.IO) {
            val filmEntity = FilmEntity(
                name = film.name,
                year = film.year,
                description = film.description
            )
            filmDao.add(filmEntity)
            return@withContext true
        }
    }

    suspend fun getEntityByName(filmName : String) : FilmEntity {
        return withContext(Dispatchers.IO) {
            return@withContext filmDao.getByName(filmName)
        }
    }

    suspend fun getAllByYearDesc(): List<FilmModel>? {
        return withContext(Dispatchers.IO) {
            val filmEntityList: List<FilmEntity>? = filmDao.getAllByYearDesc()
            return@withContext filmEntityList?.map { filmEntity -> filmEntity.toModel() }
        }
    }

    suspend fun delete(filmName: String) {
        withContext(Dispatchers.IO) {
            filmDao.deleteByName(filmName)
        }
    }

    suspend fun checkNameExists(filmName: String): Boolean {
        return withContext(Dispatchers.IO) {
            return@withContext filmDao.checkNameExists(filmName)
        }
    }
}