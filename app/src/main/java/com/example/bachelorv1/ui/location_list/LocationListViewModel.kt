package com.example.bachelorv1.ui.location_list

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.bachelorv1.data.BookDao
import com.example.bachelorv1.data.Location
import com.example.bachelorv1.data.LocationDao

class LocationListViewModel(
    private val locationDao: LocationDao
) : ViewModel() {
    val locations: MutableState<List<Location>> = mutableStateOf(locationDao.getAllLocationsOrderedByName())

    val searchQuery: MutableState<String> = mutableStateOf("")

    fun onQueryChange(text: String) {
        searchQuery.value = text
    }

    fun onSearch() {
        locations.value = locationDao.getAllLocationsOrderedByName()
    }
}