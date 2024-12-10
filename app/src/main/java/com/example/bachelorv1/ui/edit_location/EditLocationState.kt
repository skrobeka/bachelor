package com.example.bachelorv1.ui.edit_location

import com.example.bachelorv1.data.Location

data class EditLocationState(
    val location: Location? = null,
    val locationName: String = "",
    val locationBookCount: Int = 0,
    val showError: Boolean = false,
    val showErrorDelete: Boolean = false
)
