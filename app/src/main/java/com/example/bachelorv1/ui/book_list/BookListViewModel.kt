package com.example.bachelorv1.ui.book_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bachelorv1.data.Book
import com.example.bachelorv1.data.BookDao
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

class BookListViewModel(
    private val bookDao: BookDao
) : ViewModel() {
    private var books = emptyList<Book>()

    private var searchJob: Job? = null
    private var observeAllJob: Job? = null

    private val _state = MutableStateFlow(BookListState())
    val state = _state
        .onStart {
            if (books.isEmpty()) {
                observeSearchBooks()
            }
            observeAllBooks()
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

    private fun observeAllBooks() {
        observeAllJob?.cancel()
        observeAllJob = bookDao.getAllBooksOrderedByTitle()
            .onEach { books ->
                _state.update { it.copy(
                        books = books
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
                                searchResults = books
                            )
                        }
                    }

                    query.length >= 3 -> {
                        searchJob?.cancel()
                        searchJob = searchBooks(query)
                    }
                }
            }
            .launchIn(viewModelScope)
    }


    private fun searchBooks(query: String) = viewModelScope.launch {
        _state.update {
            it.copy(
                isLoading = true
            )
        }
        val searchResults = bookDao.getBookByTitle(query)
        _state.update {
            it.copy(
                searchResults = searchResults,
                isLoading = false
            )
        }
    }
}
