package com.example.bachelorv1.ui.book_list

import com.example.bachelorv1.data.Book

data class BookListState(
    val searchQuery: String = "",
    val searchResults: List<Book> = emptyList(),
    val books: List<Book> = emptyList(),
    val isLoading: Boolean = true,
    val selectedBookId: Int? = 0
)
