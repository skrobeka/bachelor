package com.example.bachelorv1.ui.location_details

import com.example.bachelorv1.data.Book
import com.example.bachelorv1.data.Location

sealed interface LocationDetailsAction {
    data class OnSelectedLocationChange(val location: Location) : LocationDetailsAction
}