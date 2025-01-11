package com.example.bachelorv1.ui.reading_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bachelorv1.data.Book
import com.example.bachelorv1.data.BookDao
import com.example.bachelorv1.ui.book_list.BookListAction
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class ReadingListViewModel(
    private val bookDao: BookDao
) : ViewModel() {
    private var readingListBooks = emptyList<Book>()

    private var observeReadingListJob: Job? = null

    private val _state = MutableStateFlow(ReadingListState())
    val state = _state
        .onStart {
            if (readingListBooks.isEmpty()) {
                observeReadingListBooks()
            }
            observeReadingListBooks()
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
            else -> Unit
        }
    }

    private fun observeReadingListBooks() {
        observeReadingListJob?.cancel()
        observeReadingListJob = bookDao.getReadingListBooks()
            .onEach { books ->
                _state.update { it.copy(
                    readingListBooks = books
                ) }
            }
            .launchIn(viewModelScope)
    }
}