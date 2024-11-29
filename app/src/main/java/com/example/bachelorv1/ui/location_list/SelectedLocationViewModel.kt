package com.example.bachelorv1.ui.location_list

import androidx.lifecycle.ViewModel
import com.example.bachelorv1.data.Location
import kotlinx.coroutines.flow.MutableStateFlow

class SelectedLocationViewModel: ViewModel() {
    private val _selectedLocation = MutableStateFlow<Location?>(null)

    fun onLocationSelect(location: Location?) {
        _selectedLocation.value = location
    }
}