package com.example.bachelorv1.ui.add_book

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bachelorv1.data.Book
import com.example.bachelorv1.data.BookDao
import com.example.bachelorv1.data.BookGenreCrossRef
import com.example.bachelorv1.data.GenreDao
import com.example.bachelorv1.data.LocationDao
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalCoroutinesApi::class)
class AddBookViewModel(
    private val bookDao: BookDao,
    private val locationDao: LocationDao,
    private val genreDao: GenreDao
) : ViewModel() {
    private val _state = MutableStateFlow(AddBookState())
    val state = _state
        .onStart {
            loadGenresAndLocations()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            _state.value
        )


    fun onAction(action: AddBookAction) {
        when (action) {
            is AddBookAction.SetPhoto -> _state.update { it.copy(photo = action.photo) }
            is AddBookAction.SetTitle -> _state.update { it.copy(title = action.title) }
            is AddBookAction.SetAuthor -> _state.update { it.copy(author = action.author) }
            is AddBookAction.SetGenre -> _state.update { it.copy(selectedGenres = action.genres) }
            is AddBookAction.SetLocation -> _state.update { it.copy(selectedLocation = action.location) }
            is AddBookAction.SetNote -> _state.update { it.copy(note = action.note) }
            is AddBookAction.SetIsGenreExpanded -> _state.update { it.copy(isGenreExpanded = action.isExpanded) }
            is AddBookAction.SetIsLocationExpanded -> _state.update { it.copy(isLocationExpanded = action.isExpanded) }
            is AddBookAction.SetCost -> _state.update { it.copy(cost = action.cost) }
            is AddBookAction.SaveBook -> {
                if (state.value.title.isBlank() || state.value.author.isBlank() || state.value.selectedLocation.isBlank()) {
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
            val book = Book(
                bookPhoto = state.value.photo,
                bookTitle = state.value.title,
                bookAuthor = state.value.author,
                locationId = locationDao.getLocationIdByName(state.value.selectedLocation),
                bookAddedDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())
            )

            bookDao.insertBook(Book(bookPhoto = book.bookPhoto, bookTitle = book.bookTitle, bookAuthor = book.bookAuthor, locationId = book.locationId, bookAddedDate = book.bookAddedDate, bookNote = state.value.note, bookCost = state.value.cost))

            val bookId = bookDao.getBookIdByTitleAuthorLocation(book.bookTitle, book.bookAuthor, book.locationId)

            if (state.value.selectedGenres.isNotEmpty()) {
                val bookGenres = state.value.selectedGenres.map { genreName -> BookGenreCrossRef(bookId, genreDao.getGenreIdByName(genreName)) }

                bookDao.insertBookGenreCrossRef(bookGenres)
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