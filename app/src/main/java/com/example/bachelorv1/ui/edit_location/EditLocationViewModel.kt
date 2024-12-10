package com.example.bachelorv1.ui.edit_location

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bachelorv1.data.LocationDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class EditLocationViewModel(
    private val editedLocationId: Int,
    private val locationDao: LocationDao
) : ViewModel() {
    private val _state = MutableStateFlow(EditLocationState())
    val state = _state
        .onStart {
                loadLocation()
            }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            _state.value
        )

    fun onAction(action: EditLocationAction) {
        when (action) {
            is EditLocationAction.SetLocationName -> _state.update { it.copy(locationName = action.locationName) }
            is EditLocationAction.SaveLocation -> {
                if (state.value.locationName.isBlank()) {
                    _state.update { it.copy(showError = true) }
                } else {
                    _state.update { it.copy(showError = false) }
                    saveLocation()
                }
            }
            is EditLocationAction.DeleteLocation -> {
                if (state.value.locationBookCount != 0) {
                    _state.update { it.copy(showErrorDelete = true) }
                }
                else {
                    _state.update { it.copy(showErrorDelete = false) }
                    deleteLocation()
                }
            }
            else -> Unit
        }
    }

    private fun saveLocation() {
        viewModelScope.launch {
            val location = locationDao.getLocationById(editedLocationId)
            if (location.locationName != state.value.locationName) {
                locationDao.updateLocation(location.copy(locationName = state.value.locationName))
            }
        }
    }

    private fun deleteLocation() {
        viewModelScope.launch {
            locationDao.deleteLocation(locationDao.getLocationById(editedLocationId))
        }
    }

    private fun loadLocation() {
        viewModelScope.launch {
            val location = locationDao.getLocationById(editedLocationId)
            _state.update {
                it.copy(
                    location = location,
                    locationName = location.locationName,
                    locationBookCount = locationDao.getLocationBookCount(editedLocationId)
                )
            }
        }
    }
}