package com.example.itis_android_tasks.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.itis_android_tasks.data.db.dao.FilmDao
import com.example.itis_android_tasks.data.db.dao.RateDao
import com.example.itis_android_tasks.data.db.dao.UserDao
import com.example.itis_android_tasks.data.db.entities.FilmEntity
import com.example.itis_android_tasks.data.db.entities.RateEntity
import com.example.itis_android_tasks.data.db.entities.UserEntity
import com.example.itis_android_tasks.data.db.entities.relations.UserFilmCrossRef

@Database(entities=[
    UserEntity::class,
    FilmEntity::class,
    RateEntity::class,
    UserFilmCrossRef::class
                   ], version=1)
abstract class FilmsAppDatabase : RoomDatabase() {
    abstract fun getUserDao() : UserDao
    abstract fun getFilmDao() : FilmDao
    abstract fun getRateDao() : RateDao
}