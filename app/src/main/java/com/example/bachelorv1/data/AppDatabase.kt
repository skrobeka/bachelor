package com.example.bachelorv1.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [Book::class, Genre::class, Location::class, Picture::class, BookGenreCrossRef::class],
    version = 1
)

abstract class AppDatabase: RoomDatabase() {

    abstract fun bookDao(): BookDao
    abstract fun locationDao(): LocationDao
    abstract fun genreDao(): GenreDao
    //abstract val pictureDao: PictureDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    "app_database"
                )
                .allowMainThreadQueries()
                .build()
            }
        }
    }
}