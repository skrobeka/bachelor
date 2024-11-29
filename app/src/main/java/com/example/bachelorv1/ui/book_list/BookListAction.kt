package com.example.bachelorv1.ui.book_list

import com.example.bachelorv1.data.Book

sealed interface BookListAction {
    data class OnSearchQueryChange(val searchedQuery: String) : BookListAction
    data class OnBookSelect(val book: Book) : BookListAction
}