package com.example.bachelorv1.data

import androidx.room.Entity

@Entity(primaryKeys = ["bookId", "genreId"],tableName = "BookGenre")
data class BookGenreCrossRef(
    val bookId: Int,
    val genreId: Int
)
