package com.example.bachelorv1.ui.location_details

import com.example.bachelorv1.data.Book

data class LocationDetailsState(
    val searchQuery: String = "",
    val searchResults: List<Book> = emptyList(),
    val booksInLocation: List<Book> = emptyList(),
    val isLoading: Boolean = true,
    val selectedBookId: Int? = 0
)
