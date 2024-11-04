package com.example.bachelorv1.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Picture")
data class Picture(
    @PrimaryKey(autoGenerate = true)
    val pictureId: Int = 0,
    val bookId: Int,
    val picturePath: String
)
