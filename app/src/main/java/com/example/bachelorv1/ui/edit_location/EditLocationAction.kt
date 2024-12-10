package com.example.bachelorv1.ui.edit_location

sealed interface EditLocationAction {
    object OnBackClick : EditLocationAction
    object OnLocationSave : EditLocationAction
    object SaveLocation : EditLocationAction
    object OnDeleteClick : EditLocationAction
    object DeleteLocation : EditLocationAction
    data class SetLocationName(val locationName: String) : EditLocationAction
}