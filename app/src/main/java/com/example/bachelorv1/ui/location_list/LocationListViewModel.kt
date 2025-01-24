package com.example.bachelorv1.ui.location_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bachelorv1.data.Location
import com.example.bachelorv1.data.LocationDao
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LocationListViewModel(
    private val locationDao: LocationDao
) : ViewModel() {
    private var locations = emptyList<Location>()
    private var searchJob: Job? = null
    private var observeAllJob: Job? = null

    private val _state = MutableStateFlow(LocationListState())
    val state = _state
        .onStart {
            if (locations.isEmpty()) {
                observeSearchLocations()
            }
            observeAllLocations()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            _state.value
        )

    fun onAction(action: LocationListAction) {
        when (action) {
            is LocationListAction.OnLocationSelect -> {
                _state.update {
                    it.copy(selectedLocationId = action.location.locationId)
                }
            }

            is LocationListAction.OnSearchQueryChange -> {
                _state.update {
                    it.copy(searchQuery = action.searchedQuery)
                }
            }
        }
    }

    private fun observeAllLocations() {
        observeAllJob?.cancel()
        observeAllJob = locationDao
            .getAllLocationsOrderedByName()
            .onEach { locations ->
                _state.update { it.copy(
                        locations = locations
                ) }
            }
            .launchIn(viewModelScope)
    }

    private fun observeSearchLocations() {
        state.map { it.searchQuery }
            .distinctUntilChanged()
            .onEach { query ->
                when {
                    query.isBlank() -> {
                        _state.update {
                            it.copy(
                                searchResults = locations
                            )
                        }
                    }

                    query.length >= 3 -> {
                        searchJob?.cancel()
                        searchJob = searchLocations(query)
                    }
                }
            }
            .launchIn(viewModelScope)
    }

    private fun searchLocations(query: String) = viewModelScope.launch {
        _state.update {
            it.copy(
                isLoading = true
            )
        }
        val searchResults = locationDao.getLocationByName(query)
        _state.update {
            it.copy(
                searchResults = searchResults,
                isLoading = false
            )
        }
    }
}