package com.example.bachelorv1.ui.location_list

import com.example.bachelorv1.data.Location

sealed interface LocationListAction {
    data class OnSearchQueryChange(val searchedQuery: String) : LocationListAction
    data class OnLocationSelect(val location: Location) : LocationListAction
}