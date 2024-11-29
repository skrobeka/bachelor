package com.example.bachelorv1.ui.favorite_book_list

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

class FavoriteBookListViewModel(
    private val bookDao: BookDao
) : ViewModel() {
    private var favoriteBooks = emptyList<Book>()

    private var searchFavoriteJob: Job? = null
    private var observeFavoriteJob: Job? = null

    private val _state = MutableStateFlow(FavoriteBookListState())
    val state = _state
        .onStart {
            if (favoriteBooks.isEmpty()) {
                observeSearchBooks()
            }
            observeFavoriteBooks()
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

    private fun observeFavoriteBooks() {
        observeFavoriteJob?.cancel()
        observeFavoriteJob = bookDao.getFavoriteBooks()
            .onEach { books ->
                _state.update { it.copy(
                    favoriteBooks = books
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
                                searchResults = favoriteBooks
                            )
                        }
                    }

                    query.length >= 3 -> {
                        searchFavoriteJob?.cancel()
                        searchFavoriteJob = searchFavoriteBooks(query)
                    }
                }
            }
            .launchIn(viewModelScope)
    }


    private fun searchFavoriteBooks(query: String) = viewModelScope.launch {
        _state.update {
            it.copy(
                isLoading = true
            )
        }
        val searchResults = bookDao.getFavoriteBookByTitle(query)
        _state.update {
            it.copy(
                searchResults = searchResults,
                isLoading = false
            )
        }
    }
}
