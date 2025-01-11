package com.example.bachelorv1.data

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Book")
data class Book(
    @PrimaryKey(autoGenerate = true)
    val bookId: Int = 0,
    val bookTitle: String,
    val bookAuthor: String,
    val locationId: Int,
    val bookIsRead: Boolean = false,
    val bookIsFavorite: Boolean = false,
    val bookIsOnReadingList: Boolean = false,
    val bookAddedDate: String,
    val bookNote: String? = null,
    val bookCost: String? = null,
    val bookPhoto: String? = null,
)
