package com.example.bachelorv1.ui.favorite_book_list

import com.example.bachelorv1.data.Book

data class FavoriteBookListState(
    val searchQuery: String = "",
    val searchResults: List<Book> = emptyList(),
    val favoriteBooks: List<Book> = emptyList(),
    val isLoading: Boolean = true,
    val selectedBookId: Int? = 0
)
