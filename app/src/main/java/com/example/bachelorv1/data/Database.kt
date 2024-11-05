package com.example.bachelorv1.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Book::class, Genre::class, Location::class, Picture::class, BookGenreCrossRef::class],
    version = 1
)

abstract class Database: RoomDatabase() {

    abstract val bookDao: BookDao
    abstract val genreDao: GenreDao
    abstract val locationDao: LocationDao
    abstract val pictureDao: PictureDao

}