package com.example.bachelorv1.ui.reading_list

import com.example.bachelorv1.data.Book

data class ReadingListState(
    val readingListBooks: List<Book> = emptyList(),
    val isLoading: Boolean = true,
    val selectedBookId: Int? = 0
)