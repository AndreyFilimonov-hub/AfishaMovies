package com.filimonov.afishamovies.data.database

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.filimonov.afishamovies.R
import com.filimonov.afishamovies.data.database.dao.CollectionDao
import com.filimonov.afishamovies.data.database.dao.CollectionMediaBannerDao
import com.filimonov.afishamovies.data.database.dao.FilmPageDao
import com.filimonov.afishamovies.data.database.dao.FilmPersonDao
import com.filimonov.afishamovies.data.database.dao.FilmSimilarMediaBannerDao
import com.filimonov.afishamovies.data.database.dao.MediaBannerDao
import com.filimonov.afishamovies.data.database.dao.PersonDao
import com.filimonov.afishamovies.data.database.model.CollectionDbModel
import com.filimonov.afishamovies.data.database.model.CollectionMediaBannerCrossRef
import com.filimonov.afishamovies.data.database.model.FilmPageDbModel
import com.filimonov.afishamovies.data.database.model.FilmPersonCrossRef
import com.filimonov.afishamovies.data.database.model.FilmSimilarMediaBannerCrossRef
import com.filimonov.afishamovies.data.database.model.MediaBannerDbModel
import com.filimonov.afishamovies.data.database.model.PersonDbModel
import com.filimonov.afishamovies.domain.enums.DefaultCollection

@Database(
    entities = [
        CollectionDbModel::class,
        MediaBannerDbModel::class,
        CollectionMediaBannerCrossRef::class,
        FilmPageDbModel::class,
        PersonDbModel::class,
        FilmPersonCrossRef::class,
        FilmSimilarMediaBannerCrossRef::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AfishaDataBase() : RoomDatabase() {

    companion object {

        @Volatile
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
                )
                    .addCallback(object  : Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)

                            db.execSQL("""
                                INSERT INTO collections (collectionKey, name, isDefault)
                                VALUES
                                ('${DefaultCollection.WATCHED.key}' ,'${application.resources.getString(R.string.watched)}', 1),
                                ('${DefaultCollection.INTERESTED.key}' ,'${application.resources.getString(R.string.you_have_interested)}', 1),
                                ('${DefaultCollection.LIKED.key}' ,'${application.resources.getString(R.string.liked)}', 1),
                                ('${DefaultCollection.WANT_TO_WATCH.key}' ,'${application.resources.getString(R.string.want_to_watch)}', 1)
                            """.trimIndent())
                        }
                    })
                    .build().also { instance = it }
            }
        }
    }

    abstract fun collectionDao(): CollectionDao
    abstract fun mediaBannerDao(): MediaBannerDao
    abstract fun collectionMediaBannerDao(): CollectionMediaBannerDao
    abstract fun filmPageDao(): FilmPageDao
    abstract fun personDao(): PersonDao
    abstract fun filmPersonDao(): FilmPersonDao
    abstract fun filmSimilarMediaBannerDao(): FilmSimilarMediaBannerDao
}