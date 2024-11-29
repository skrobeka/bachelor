package com.example.bachelorv1.ui.location_list

import com.example.bachelorv1.data.Location

data class LocationListState(
    val searchQuery: String = "",
    val searchResults: List<Location> = emptyList(),
    val locations: List<Location> = emptyList(),
    val isLoading: Boolean = true,
    val selectedLocationId: Int? = 0
)
