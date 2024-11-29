package com.example.bachelorv1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.example.bachelorv1.data.AppDatabase
import com.example.bachelorv1.ui.add_book.AddBookScreenRoot
import com.example.bachelorv1.ui.book_details.BookDetailsScreenRoot
import com.example.bachelorv1.ui.book_list.BookListScreenRoot
import com.example.bachelorv1.ui.book_list.SelectedBookViewModel
import com.example.bachelorv1.ui.edit_book.EditBookScreenRoot
import com.example.bachelorv1.ui.favorite_book_list.FavoriteBookListScreenRoot
import com.example.bachelorv1.ui.location_details.LocationDetailsScreenRoot
import com.example.bachelorv1.ui.location_list.LocationListScreenRoot
import com.example.bachelorv1.ui.location_list.SelectedLocationViewModel
import com.example.bachelorv1.ui.theme.BachelorV1Theme


class MainActivity : ComponentActivity() {
    companion object {
        lateinit var db: AppDatabase
    }

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = AppDatabase.getDatabase(this)
        enableEdgeToEdge()
        setContent {
            BachelorV1Theme {
                Surface {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Route.BookGraph
                    ) {
                        navigation<Route.BookGraph>(
                            startDestination = Route.BookList,
                        ) {
                            composable<Route.BookList> {
                                val selectedBookViewModel = it.sharedViewModel<SelectedBookViewModel>(navController)

                                BookListScreenRoot(
                                    onBookSelect = { book ->
                                        selectedBookViewModel.onBookSelect(book)
                                        navController.navigate(Route.BookDetails(book.bookId))
                                    }
                                )
                            }

                            composable<Route.BookDetails> {
                                BookDetailsScreenRoot(
                                    onBackClick = { navController.popBackStack() },
                                    onEditClick = {
                                        navController.navigate(Route.EditBook(it.arguments?.getInt("bookId") ?: -1))
                                    },
                                    bookId = it.arguments?.getInt("bookId") ?: -1
                                )
                            }

                            composable<Route.FavoriteList> {
                                val selectedBookViewModel = it.sharedViewModel<SelectedBookViewModel>(navController)

                                FavoriteBookListScreenRoot(
                                    onBookSelect = { book ->
                                        selectedBookViewModel.onBookSelect(book)
                                        navController.navigate(Route.BookDetails(book.bookId))
                                    }
                                )
                            }

                            composable<Route.LocationList> {
                                val selectedLocationViewModel = it.sharedViewModel<SelectedLocationViewModel>(navController)

                                LocationListScreenRoot(
                                    onLocationSelect = { location ->
                                        selectedLocationViewModel.onLocationSelect(location)
                                        navController.navigate(Route.LocationDetails(location.locationId))
                                    }
                                )
                            }

                            composable<Route.LocationDetails> {
                                LocationDetailsScreenRoot(
                                    onBookSelect = { book ->
                                        navController.navigate(Route.BookDetails(book.bookId))
                                    },
                                    locationId = it.arguments?.getInt("locationId") ?: -1
                                )
                            }

                            composable<Route.AddBook> {
                                AddBookScreenRoot(
                                    onBackClick = { navController.popBackStack() },
                                    onBookSave = { navController.navigate(Route.BookList) { popUpTo(Route.AddBook) {inclusive = true} } }
                                )
                            }

                            composable<Route.EditBook> {
                                EditBookScreenRoot(
                                    onBackClick = { navController.popBackStack() },
                                    onBookSave = { navController.navigate(Route.BookDetails(it.arguments?.getInt("bookId") ?: -1)) {popUpTo(Route.BookDetails(it.arguments?.getInt("bookId") ?: -1)) {inclusive = true}} },
                                    bookId = it.arguments?.getInt("bookId") ?: -1
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}


@Composable
inline fun <reified T : ViewModel> NavBackStackEntry.sharedViewModel(
    navController: NavController
): T {
    val navGraphRoute = destination.parent?.route ?: return viewModel<T>()
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }
    return viewModel(parentEntry)
}
