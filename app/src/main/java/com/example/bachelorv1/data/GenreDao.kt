package com.example.bachelorv1.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface GenreDao {

    //Queries
    @Query("SELECT * FROM genre ORDER BY genreName ASC")
    fun getAllGenresOrderedByName(): LiveData<List<Genre>>

    @Query("SELECT * FROM genre WHERE genreName = :genreName ORDER BY genreName ASC")
    fun getGenreByName(genreName: String): LiveData<Genre>

}