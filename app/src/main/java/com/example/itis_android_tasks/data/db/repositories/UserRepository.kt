package com.example.itis_android_tasks.data.db.repositories

import com.example.itis_android_tasks.data.db.entities.UserEntity
import com.example.itis_android_tasks.data.db.entities.relations.UserFilmCrossRef
import com.example.itis_android_tasks.data.db.entities.relations.UserWithFavoriteFilms
import com.example.itis_android_tasks.data.model.FilmModel
import com.example.itis_android_tasks.data.model.UserModel
import com.example.itis_android_tasks.di.ServiceLocator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object UserRepository {

    private val userDao = ServiceLocator.getDbInstance().getUserDao()

    suspend fun add(user: UserModel) : Boolean {
        return withContext(Dispatchers.IO) {
            if (userDao.checkPhoneExist(user.phone) || userDao.checkEmailExist(user.email)) {
                return@withContext false
            }
            val userEntity = UserEntity(
                name = user.name,
                email = user.email,
                phone = user.phone,
                password = user.password,
            )
            userDao.add(userEntity)
            return@withContext true
        }
    }

    suspend fun delete(user: UserEntity) {
        withContext(Dispatchers.IO) {
            userDao.delete(user)
        }
    }

    suspend fun getUserByData(email: String, password: String): UserEntity {
        return withContext(Dispatchers.IO) {
            return@withContext userDao.getUserByData(email, password)
        }
    }

    suspend fun getUserById(id: Int): UserEntity {
        return withContext(Dispatchers.IO) {
            return@withContext userDao.getUserById(id)
        }
    }

    suspend fun updatePhone(id: Int, phone: String) : Boolean {
        return withContext(Dispatchers.IO) {
            if (userDao.checkPhoneExist(phone)) {
                return@withContext false
            }
            userDao.updatePhone(id, phone)
            return@withContext true
        }
    }

    suspend fun updatePassword(id: Int, password: String) : Boolean {
        return withContext(Dispatchers.IO) {
            userDao.updatePassword(id, password)
            return@withContext true
        }
    }

    suspend fun getAllFavorites(id: Int): List<FilmModel> {
        return withContext(Dispatchers.IO) {
            val userFavoriteFilms = userDao.getAllFavorites(id)
            return@withContext userFavoriteFilms.films.map { filmEntity -> filmEntity.toModel() }
        }
    }

    suspend fun addFavorite(userId: Int, filmName: String) {
        withContext(Dispatchers.IO) {
            val filmEntity = ServiceLocator.getDbInstance().getFilmDao().getByName(filmName)
            getUserById(userId).let { userEntity ->
                filmEntity.let { filmEntity ->
                    userDao.addUserFilmCrossRef(
                        UserFilmCrossRef(userEntity.userId, filmEntity.filmId)
                    )
                }
            }
        }
    }

    suspend fun isFavorite(userId: Int, filmName: String): Boolean {
        val userFavouriteFilms: UserWithFavoriteFilms =
            userDao.getAllFavorites(userId)
        return userFavouriteFilms.films.any { filmEntity ->
            filmEntity.name == filmName
        }
    }

    suspend fun deleteFavorite(id: Int, filmName: String) {
        withContext(Dispatchers.IO) {
            val filmEntity = ServiceLocator.getDbInstance().getFilmDao().getByName(filmName)
            getUserById(id).let { userEntity ->
                filmEntity.let { filmEntity ->
                    userDao.deleteUserFilmCrossRef(
                        UserFilmCrossRef(userEntity.userId, filmEntity.filmId)
                    )
                }
            }
        }
    }
}