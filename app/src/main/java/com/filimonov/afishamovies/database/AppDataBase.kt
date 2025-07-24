package com.filimonov.afishamovies.database

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [MovieDbModel::class],
    version = 1,
    exportSchema = false
)
abstract class AppDataBase : RoomDatabase() {



    companion object {

        private var instance: AppDataBase? = null
        private val LOCK = Any()

        private const val DB_NAME = "movies.db"

        fun getInstance(application: Application): AppDataBase {
            instance?.let { return it }
            synchronized(LOCK) {
                instance?.let { return it }
                return Room.databaseBuilder(
                    application,
                    AppDataBase::class.java,
                    DB_NAME
                ).build().also { instance = it }
            }
        }
    }
}