package com.example.bachelorv1.data

import androidx.room.*

data class BookWithGenres(
    @Embedded val book: Book,
    @Relation(
        parentColumn = "bookId",
        entityColumn = "genreId",
        associateBy = Junction(BookGenreCrossRef::class)
    )
    val genres: List<Genre>
)
