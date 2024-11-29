package com.example.bachelorv1.ui.edit_book

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bachelorv1.data.BookDao
import com.example.bachelorv1.data.BookGenreCrossRef
import com.example.bachelorv1.data.GenreDao
import com.example.bachelorv1.data.LocationDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class EditBookViewModel(
    private val editedBookId: Int,
    private val bookDao: BookDao,
    private val locationDao: LocationDao,
    private val genreDao: GenreDao
) :ViewModel() {
    private val bookId = editedBookId

    private val _state = MutableStateFlow(EditBookState())
    val state = _state
        .onStart {
            loadBook()
            loadGenresAndLocations()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            _state.value
        )

    fun onAction(action: EditBookAction) {
        when (action) {
            is EditBookAction.SetTitle -> _state.update { it.copy(title = action.title) }
            is EditBookAction.SetAuthor -> _state.update { it.copy(author = action.author) }
            is EditBookAction.SetGenre -> _state.update { it.copy(selectedGenres = action.genres) }
            is EditBookAction.SetLocation -> _state.update { it.copy(selectedLocation = action.location) }
            is EditBookAction.SetEdition -> _state.update { it.copy(edition = action.edition) }
            is EditBookAction.SetIsGenreExpanded -> _state.update { it.copy(isGenreExpanded = action.isExpanded) }
            is EditBookAction.SetIsLocationExpanded -> _state.update { it.copy(isLocationExpanded = action.isExpanded) }
            is EditBookAction.SaveBook -> {
                if (state.value.title.isBlank() || state.value.author.isBlank() || state.value.selectedLocation.isBlank() || state.value.selectedGenres.isEmpty()) {
                    _state.update { it.copy(showError = true) }
                } else {
                    _state.update { it.copy(showError = false) }
                    saveBook()
                }
            }
            else -> Unit
        }
    }

    private fun saveBook() {
        viewModelScope.launch {
            val book = bookDao.getBookById(bookId)
            val bookGenres = state.value.selectedGenres.map { genreDao.getGenreIdByName(it) }
            if (book.bookTitle != state.value.title) {
                bookDao.updateBookTitle(bookId, state.value.title)
            }
            if (book.bookAuthor != state.value.author) {
                bookDao.updateBookTitle(bookId, state.value.author)
            }
            if (book.locationId != locationDao.getLocationIdByName(state.value.selectedLocation)) {
                bookDao.updateBookLocation(bookId, locationDao.getLocationIdByName(state.value.selectedLocation))
            }
            if (bookGenres != state.value.selectedGenres) {
                bookDao.deleteBookGenreCrossRef(bookId)
                val newBookGenres = state.value.selectedGenres.map { genreName -> BookGenreCrossRef(bookId, genreDao.getGenreIdByName(genreName)) }
                bookDao.insertBookGenreCrossRef(newBookGenres)
            }
            if (book.bookEdition != state.value.edition) {
                bookDao.updateBookEdition(bookId, state.value.edition.toString())
            }
        }
    }

    private fun loadBook() {
        viewModelScope.launch {
            val book = bookDao.getBookById(bookId)
            val bookGenresIds = bookDao.getBookWithGenres(bookId)
            _state.update {
                it.copy(
                    book = book,
                    title = book.bookTitle,
                    author = book.bookAuthor,
                    selectedLocation = locationDao.getLocationNameById(book.locationId),
                    selectedGenres = bookGenresIds.map { genreId -> genreDao.getGenreNameById(genreId) },
                    edition = book.bookEdition,
                    isLoading = false
                )
            }
        }
    }

    private fun loadGenresAndLocations() {
        viewModelScope.launch {
            val genres = genreDao.getAllGenresOrderedByName().first()
            val locations = locationDao.getAllLocationsOrderedByName().first()
            _state.update {
                it.copy(
                    genres = genres,
                    locations = locations
                )
            }
        }
    }

}