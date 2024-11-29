package com.example.bachelorv1.ui.edit_book

import com.example.bachelorv1.data.Genre
import com.example.bachelorv1.data.Location

data class EditBookState(
    val title: String = "",
    val author: String = "",
    val locations: List<Location> = emptyList(),
    val selectedLocation: String = "",
    val genres: List<Genre> = emptyList(),
    val selectedGenres: List<String> = emptyList(),
    val isGenreExpanded: Boolean = false,
    val isLocationExpanded: Boolean = false,
    val isLoading: Boolean = false,
    val showError: Boolean = false
)
