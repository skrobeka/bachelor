package com.example.bachelorv1.ui.add_location

sealed interface AddLocationAction {
    object OnBackClick : AddLocationAction
    object OnLocationSave : AddLocationAction
    object SaveLocation : AddLocationAction
    data class SetLocationName(val locationName: String) : AddLocationAction
}