package com.example.bachelorv1.ui.add_book

sealed interface AddBookAction {
    object OnBackClick : AddBookAction
    object OnBookSave : AddBookAction
    object SaveBook : AddBookAction
    data class SetTitle(val title: String) : AddBookAction
    data class SetAuthor(val author: String) : AddBookAction
    data class SetGenre(val genres: List<String>) : AddBookAction
    data class SetLocation(val location: String) : AddBookAction
    data class SetEdition(val edition: String) : AddBookAction
    data class SetIsGenreExpanded(val isExpanded: Boolean) : AddBookAction
    data class SetIsLocationExpanded(val isExpanded: Boolean) : AddBookAction
}