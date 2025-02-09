package com.example.itis_android_tasks.data.db.repositories

import com.example.itis_android_tasks.data.db.entities.RateEntity
import com.example.itis_android_tasks.di.ServiceLocator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object RateRepository {

    private val rateDao = ServiceLocator.getDbInstance().getRateDao()

    suspend fun add(rate : RateEntity) {
        withContext(Dispatchers.IO) {
            rateDao.add(rate)
        }
    }

    suspend fun get(filmId : Int, userId : Int) : RateEntity? {
        return withContext(Dispatchers.IO) {
            return@withContext rateDao.get(filmId, userId)
        }
    }

    suspend fun getAvg(filmId : Int) : Float {
        return withContext(Dispatchers.IO) {
            return@withContext rateDao.getAverageRate(filmId)
        }
    }
}