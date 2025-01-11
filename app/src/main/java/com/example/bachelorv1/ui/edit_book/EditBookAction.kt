package com.example.bachelorv1.ui.edit_book

sealed interface EditBookAction {
    object OnBackClick : EditBookAction
    object OnBookSave : EditBookAction
    object SaveBook : EditBookAction
    data class SetPhoto(val photo: String) : EditBookAction
    data class SetTitle(val title: String) : EditBookAction
    data class SetAuthor(val author: String) : EditBookAction
    data class SetGenre(val genres: List<String>) : EditBookAction
    data class SetLocation(val location: String) : EditBookAction
    data class SetNote(val note: String) : EditBookAction
    data class SetCost(val cost: String) : EditBookAction
    data class SetIsGenreExpanded(val isExpanded: Boolean) : EditBookAction
    data class SetIsLocationExpanded(val isExpanded: Boolean) : EditBookAction
}