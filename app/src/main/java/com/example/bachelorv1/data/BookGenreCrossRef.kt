package com.example.bachelorv1.data

import androidx.room.Entity

@Entity(primaryKeys = ["book_id", "genre_id"],tableName = "BookGenre")
data class BookGenreCrossRef(
    val book: Book,
    val genre: Genre
)
