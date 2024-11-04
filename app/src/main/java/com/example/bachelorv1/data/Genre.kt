package com.example.bachelorv1.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Genre")
data class Genre(
    @PrimaryKey(autoGenerate = true)
    val genreId: Int = 0,
    val genreName: String
)
