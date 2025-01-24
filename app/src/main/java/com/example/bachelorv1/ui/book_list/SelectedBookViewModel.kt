package com.example.bachelorv1.ui.book_list

import androidx.lifecycle.ViewModel
import com.example.bachelorv1.data.Book
import kotlinx.coroutines.flow.MutableStateFlow

class SelectedBookViewModel: ViewModel() {
    private val _selectedBook = MutableStateFlow<Book?>(null)

    fun onBookSelect(book: Book?) {
        _selectedBook.value = book
    }
}