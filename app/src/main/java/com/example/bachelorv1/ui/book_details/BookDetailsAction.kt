package com.example.bachelorv1.ui.book_details

import com.example.bachelorv1.data.Book

sealed interface BookDetailsAction {
    data object OnBackClick : BookDetailsAction
    data object OnFavoriteClick : BookDetailsAction
    data object OnIsReadClick : BookDetailsAction
    data object OnEditClick : BookDetailsAction
    data object OnDeleteClick : BookDetailsAction
}