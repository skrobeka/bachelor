package com.example.bachelorv1.ui.book_details

import androidx.lifecycle.ViewModel
import com.example.bachelorv1.data.BookDao
import com.example.bachelorv1.data.GenreDao
import com.example.bachelorv1.data.LocationDao

class BookDetailsViewModel(
    private val selectedBookId: Int,
    private val bookDao: BookDao,
    private val locationDao: LocationDao,
    private val genreDao: GenreDao
) : ViewModel() {
    val book = bookDao.getBookById(selectedBookId)
    val bookGenres: List<Int> = bookDao.getBookWithGenres(selectedBookId)

    fun getBookGenreNames(): List<String> {
        return bookGenres.map { genreDao.getGenreNameById(it) }
    }

    fun getBookLocationName(): String {
        return locationDao.getLocationNameById(book.locationId)
    }
}