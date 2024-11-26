package com.example.bachelorv1.ui.book_list

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.bachelorv1.data.Book
import com.example.bachelorv1.data.BookDao
import com.example.bachelorv1.data.LocationDao

class BookListViewModel(
    private val bookDao: BookDao,
    private val locationDao: LocationDao
) : ViewModel() {
    val books: MutableState<List<Book>> = mutableStateOf(bookDao.getAllBooksOrderedByTitle())

    val searchQuery: MutableState<String> = mutableStateOf("")

    fun getLocationNameById(locationId: Int): String {
        return locationDao.getLocationNameById(locationId)
    }

    fun onQueryChange(text: String) {
        searchQuery.value = text
    }

    fun onSearch() {
        books.value = bookDao.getBookByTitle(searchQuery.value)
    }

}