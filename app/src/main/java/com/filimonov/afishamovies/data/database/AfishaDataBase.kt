package com.filimonov.afishamovies.data.database

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.filimonov.afishamovies.data.database.dao.CollectionDao
import com.filimonov.afishamovies.data.database.dao.CollectionMediaBannerDao
import com.filimonov.afishamovies.data.database.dao.MediaBannerDao
import com.filimonov.afishamovies.data.database.model.CollectionDbModel
import com.filimonov.afishamovies.data.database.model.CollectionMediaBannerCrossRef
import com.filimonov.afishamovies.data.database.model.MediaBannerDbModel

@Database(
    entities = [CollectionDbModel::class, MediaBannerDbModel::class, CollectionMediaBannerCrossRef::class],
    version = 1,
    exportSchema = false
)
abstract class AfishaDataBase() : RoomDatabase() {

    companion object {

        private var instance: AfishaDataBase? = null
        private const val DB_NAME = "afisha.db"
        private val lock = Any()

        fun getInstance(application: Application): AfishaDataBase {
            instance?.let { return it }
            synchronized(lock) {
                instance?.let { return it }
                return Room.databaseBuilder(
                    application,
                    AfishaDataBase::class.java,
                    DB_NAME
                ).build().also { instance = it }
            }
        }
    }

    abstract fun collectionDao(): CollectionDao
    abstract fun mediaBannerDao(): MediaBannerDao
    abstract fun collectionMediaBannerDao(): CollectionMediaBannerDao
}