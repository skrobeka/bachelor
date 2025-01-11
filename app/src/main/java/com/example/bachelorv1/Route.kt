package com.example.bachelorv1

import kotlinx.serialization.Serializable


sealed interface Route {

    @Serializable
    data object BookGraph: Route

    @Serializable
    data object BookList : Route

    @Serializable
    data class BookDetails(val bookId: Int) : Route

    @Serializable
    data object LocationList : Route

    @Serializable
    data class LocationDetails(val locationId: Int) : Route

    @Serializable
    data object FavoriteList : Route

    @Serializable
    data object ReadingList : Route

    @Serializable
    data object AddBook : Route

    @Serializable
    data object AddLocation : Route

    @Serializable
    data class EditBook(val bookId: Int) : Route

    @Serializable
    data class EditLocation(val locationId: Int) : Route
}