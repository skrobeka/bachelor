package com.example.bachelorv1.ui.book_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bachelorv1.data.BookDao
import com.example.bachelorv1.data.GenreDao
import com.example.bachelorv1.data.LocationDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BookDetailsViewModel(
    private val selectedBookId: Int,
    private val bookDao: BookDao,
    private val locationDao: LocationDao,
    private val genreDao: GenreDao
) : ViewModel() {
    private val bookId = selectedBookId

    private val _state = MutableStateFlow(BookDetailsState())
    val state = _state
        .onStart {
            loadBook()
            observeFavoriteStatus()
            observeIsReadStatus()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            _state.value
        )

    fun onAction(action: BookDetailsAction) {
        when (action) {
            is BookDetailsAction.OnFavoriteClick -> {
                viewModelScope.launch {
                    val currentIsFavorite = state.value.isFavorite
                    bookDao.updateBookFavoriteStatus(selectedBookId, !currentIsFavorite)
                }
            }
            is BookDetailsAction.OnIsReadClick -> {
                viewModelScope.launch {
                    val currentIsRead = state.value.isRead
                    bookDao.updateBookReadStatus(selectedBookId, !currentIsRead)
                }
            }
            is BookDetailsAction.OnDeleteClick -> {
                deleteBook()
            }
            else -> Unit
        }
    }

    private fun loadBook() {
        viewModelScope.launch {
            val book = bookDao.getBookById(bookId)
            val bookGenresIds = bookDao.getBookWithGenres(bookId)
            _state.update {
                it.copy(
                    book = book,
                    bookLocation = locationDao.getLocationNameById(book.locationId),
                    bookGenres = bookGenresIds.map { genreId -> genreDao.getGenreNameById(genreId) },
                    isRead = book.bookIsRead,
                    isLoading = false
                )
            }
        }
    }

    private fun deleteBook() {
        viewModelScope.launch {
            bookDao.deleteBook(bookDao.getBookById(bookId))
            bookDao.deleteBookGenreCrossRef(bookId)
        }
    }

    private fun observeFavoriteStatus() {
        bookDao.isBookFavorite(bookId)
            .onEach { isFavorite ->
                _state.update {
                    it.copy(
                        isFavorite = isFavorite
                    )
                }
            }
            .launchIn(viewModelScope)
    }

    private fun observeIsReadStatus() {
        bookDao.isBookRead(bookId)
            .onEach { isRead ->
                _state.update {
                    it.copy(
                        isRead = isRead
                    )
                }
            }
            .launchIn(viewModelScope)
    }
}
