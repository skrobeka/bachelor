package com.example.bachelorv1.data

sealed interface BookEvent {
    object SaveBook: BookEvent
    data class SetBookTitle(val bookTitle: String): BookEvent
    data class SetBookAuthor(val bookAuthor: String): BookEvent
    data class SetBookGenre(val bookGenre: String): BookEvent
    data class SetBookLocation(val bookLocation: String): BookEvent
    //data class SetBookImage(val bookImage: String): BookEvent

    data class DeleteBook(val book: Book): BookEvent
    data class EditBook(val book: Book): BookEvent

    data class SortBooks(val sortType: BookSortType): BookEvent
}