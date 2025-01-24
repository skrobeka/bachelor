package com.example.bachelorv1.ui.book_details

import com.example.bachelorv1.data.Book

data class BookDetailsState(
    val isLoading: Boolean = true,
    val isOnReadingList: Boolean = false,
    val isFavorite: Boolean = false,
    val isRead: Boolean = false,
    val book: Book? = null,
    val bookLocation: String = "",
    val bookGenres: List<String> = emptyList()
)
