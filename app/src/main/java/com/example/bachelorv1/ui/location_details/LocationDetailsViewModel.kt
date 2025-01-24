package com.example.bachelorv1.ui.location_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bachelorv1.data.Book
import com.example.bachelorv1.data.BookDao
import com.example.bachelorv1.ui.book_list.BookListAction
import com.example.bachelorv1.ui.book_list.BookListState
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LocationDetailsViewModel(
    private val selectedLocationId: Int,
    private val bookDao: BookDao
) : ViewModel() {
    private var booksInLocation = emptyList<Book>()

    private var searchLocationJob: Job? = null
    private var observeLocationBooksJob: Job? = null

    private val _state = MutableStateFlow(LocationDetailsState())
    val state = _state
        .onStart {
            if (booksInLocation.isEmpty()) {
                observeSearchBooks()
            }
            observeLocationBooks()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            _state.value
        )

    fun onAction(action: BookListAction) {
        when (action) {
            is BookListAction.OnBookSelect -> {
                _state.update {
                    it.copy(selectedBookId = action.book.bookId)
                }
            }

            is BookListAction.OnSearchQueryChange -> {
                _state.update {
                    it.copy(searchQuery = action.searchedQuery)
                }
            }
        }
    }

    private fun observeLocationBooks() {
        observeLocationBooksJob?.cancel()
        observeLocationBooksJob = bookDao.getBooksByLocation(selectedLocationId)
            .onEach { books ->
                _state.update { it.copy(
                    booksInLocation = books
                ) }
            }
            .launchIn(viewModelScope)
    }

    private fun observeSearchBooks() {
        state.map { it.searchQuery }
            .distinctUntilChanged()
            .onEach { query ->
                when {
                    query.isBlank() -> {
                        _state.update {
                            it.copy(
                                searchResults = booksInLocation
                            )
                        }
                    }

                    query.length >= 3 -> {
                        searchLocationJob?.cancel()
                        searchLocationJob = searchLocationBooks(query)
                    }
                }
            }
            .launchIn(viewModelScope)
    }


    private fun searchLocationBooks(query: String) = viewModelScope.launch {
        _state.update {
            it.copy(
                isLoading = true
            )
        }
        val searchResults = bookDao.getLocationBookByTitle(selectedLocationId, query)
        _state.update {
            it.copy(
                searchResults = searchResults,
                isLoading = false
            )
        }
    }
}
