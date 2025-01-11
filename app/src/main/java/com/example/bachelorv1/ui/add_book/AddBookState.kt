package com.example.bachelorv1.ui.add_book

import android.net.Uri
import com.example.bachelorv1.data.Genre
import com.example.bachelorv1.data.Location

data class AddBookState(
    val photo: String = "",
    val title: String = "",
    val author: String = "",
    val locations: List<Location> = emptyList(),
    val selectedLocation: String = "",
    val genres: List<Genre> = emptyList(),
    val selectedGenres: List<String> = emptyList(),
    val note: String? = "",
    val cost: String? = "",
    val isGenreExpanded: Boolean = false,
    val isLocationExpanded: Boolean = false,
    val isLoading: Boolean = false,
    val showError: Boolean = false
)
