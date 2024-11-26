package com.example.bachelorv1.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface GenreDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertGenre(genre: Genre)

    //Queries
    @Query("SELECT * FROM genre ORDER BY genreName ASC")
    fun getAllGenresOrderedByName(): List<Genre>

    @Query("SELECT * FROM genre WHERE genreName = :genreName ORDER BY genreName ASC")
    fun getGenreByName(genreName: String): Genre

    @Query("SELECT genreId FROM genre WHERE genreName = :genreName LIMIT 1")
    fun getGenreIdByName(genreName: String): Int

    @Query("SELECT genreName FROM genre WHERE genreId = :genreId LIMIT 1")
    fun getGenreNameById(genreId: Int): String
}