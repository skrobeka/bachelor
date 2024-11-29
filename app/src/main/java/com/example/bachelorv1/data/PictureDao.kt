package com.example.bachelorv1.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface PictureDao {

    @Upsert
    suspend fun insertPicture(picture: Picture)

    @Delete
    suspend fun deletePicture(picture: Picture)

    //Queries


}