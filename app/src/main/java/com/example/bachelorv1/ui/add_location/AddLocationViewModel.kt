package com.example.bachelorv1.ui.add_location

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bachelorv1.data.Location
import com.example.bachelorv1.data.LocationDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AddLocationViewModel(
    private val locationDao: LocationDao
) : ViewModel() {
    private val _state = MutableStateFlow(AddLocationState())
    val state = _state
        .onStart {
            viewModelScope.launch {
                _state.update {
                    it.copy(

                    )
                }
            }
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            _state.value
        )

    fun onAction(action: AddLocationAction) {
        when (action) {
            is AddLocationAction.SetLocationName -> _state.update { it.copy(locationName = action.locationName) }
            is AddLocationAction.SaveLocation -> {
                if (state.value.locationName.isBlank()) {
                    _state.update { it.copy(showError = true) }
                } else {
                    _state.update { it.copy(showError = false) }
                    saveLocation()
                }
            }
            else -> Unit
        }
    }

    fun saveLocation() {
        viewModelScope.launch {
            locationDao.insertLocation(Location(locationName = state.value.locationName))
        }
    }
}