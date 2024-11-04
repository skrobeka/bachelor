package com.example.bachelorv1.data

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "Book")
data class Book(
    @PrimaryKey(autoGenerate = true)
    val bookId: Int = 0,
    val bookTitle: String,
    val bookAuthor: String,
    val locationId: Int,
    val bookAddedDate: Date = Date(),

    @Embedded val picture: Picture? = null,
)
