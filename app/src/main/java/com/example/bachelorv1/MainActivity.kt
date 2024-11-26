package com.example.bachelorv1

import android.R.id
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.Color.Companion.Yellow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.bachelorv1.data.AppDatabase
import com.example.bachelorv1.data.BookDao
import com.example.bachelorv1.data.Genre
import com.example.bachelorv1.ui.add_book.AddBookScreen
import com.example.bachelorv1.ui.add_book.AddBookViewModel
import com.example.bachelorv1.ui.book_details.BookDetailsScreen
import com.example.bachelorv1.ui.book_details.BookDetailsViewModel
import com.example.bachelorv1.ui.book_list.BookListScreen
import com.example.bachelorv1.ui.book_list.BookListViewModel
import com.example.bachelorv1.ui.location_list.LocationListScreen
import com.example.bachelorv1.ui.location_list.LocationListViewModel
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
                val navController = rememberNavController()
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavHost(navController = navController, startDestination = "book_list") {
                        composable("book_list") {
                            Scaffold(
                                topBar = {
                                    CenterAlignedTopAppBar(
                                        title = {
                                            Row(
                                                verticalAlignment = CenterVertically,
                                            ) {
                                                Image(
                                                    modifier = Modifier
                                                        .size(32.dp)
                                                        .clip(MaterialTheme.shapes.small),
                                                    painter = painterResource(R.drawable.ic_launcher_playstore),
                                                    contentDescription = "App logo"
                                                )
                                                Spacer(modifier = Modifier.width(8.dp))
                                                Text(
                                                    text = "BookApp",
                                                    style = MaterialTheme.typography.displaySmall
                                                )
                                            }
                                        },
                                        colors = TopAppBarColors(
                                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                                            scrolledContainerColor = MaterialTheme.colorScheme.primaryContainer,
                                            titleContentColor = MaterialTheme.colorScheme.primary,
                                            navigationIconContentColor = MaterialTheme.colorScheme.primary,
                                            actionIconContentColor = MaterialTheme.colorScheme.primary,
                                        )
                                    )
                                },
                                bottomBar = {
                                    NavigationBar {
                                        NavigationBarItem(
                                            selected = true,
                                            onClick = { navController.navigate("book_list") },
                                            icon = { Icon(painterResource(R.drawable.book_icon), contentDescription = "Book icon") },
                                            label = { Text("Books") }
                                        )
                                        NavigationBarItem(
                                            selected = false,
                                            onClick = { navController.navigate("location_list") },
                                            icon = { Icon(painterResource(R.drawable.shelves_icon), contentDescription = "Location icon") },
                                            label = { Text("Locations") }
                                        )
                                        NavigationBarItem(
                                            selected = false,
                                            onClick = {  },
                                            icon = { Icon(Icons.Default.FavoriteBorder, contentDescription = "Favorites icon") },
                                            label = { Text("Favorites") }
                                        )
                                        NavigationBarItem(
                                            selected = false,
                                            onClick = {  },
                                            icon = {  Icon(painterResource(R.drawable.star_icon), contentDescription = "Wishlist icon")  },
                                            label = { Text("Wishlist") }
                                        )
                                    }
                                },
                                floatingActionButton = {
                                    FloatingActionButton(
                                        onClick = { navController.navigate("add_book") },
                                        containerColor = MaterialTheme.colorScheme.secondaryContainer,
                                        contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                                    ) {
                                        Icon(Icons.Default.Add, contentDescription = "Add book/location")
                                    }
                                }
                            ) { it ->
                                Column(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(top = 72.dp, bottom = it.calculateBottomPadding())
                                ) {
                                    BookListScreen(navController = navController, viewModel = BookListViewModel(bookDao = db.bookDao(), locationDao = db.locationDao()))
                                }
                            }
                        }
                        composable("add_book") {
                            AddBookScreen(navController = navController, viewModel = AddBookViewModel(bookDao = db.bookDao(), locationDao = db.locationDao(), genreDao = db.genreDao()))
                        }
                        composable("location_list") {
                            Scaffold(
                                topBar = {
                                    CenterAlignedTopAppBar(
                                        title = {
                                            Row(
                                                verticalAlignment = CenterVertically,
                                            ) {
                                                Image(
                                                    modifier = Modifier
                                                        .size(32.dp)
                                                        .clip(MaterialTheme.shapes.small),
                                                    painter = painterResource(R.drawable.ic_launcher_playstore),
                                                    contentDescription = "App logo"
                                                )
                                                Spacer(modifier = Modifier.width(8.dp))
                                                Text(
                                                    text = "BookApp",
                                                    style = MaterialTheme.typography.displaySmall
                                                )
                                            }
                                        },
                                        colors = TopAppBarColors(
                                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                                            scrolledContainerColor = MaterialTheme.colorScheme.primaryContainer,
                                            titleContentColor = MaterialTheme.colorScheme.primary,
                                            navigationIconContentColor = MaterialTheme.colorScheme.primary,
                                            actionIconContentColor = MaterialTheme.colorScheme.primary,
                                        )
                                    )
                                },
                                bottomBar = {
                                    NavigationBar {
                                        NavigationBarItem(
                                            selected = false,
                                            onClick = { navController.navigate("book_list") },
                                            icon = { Icon(painterResource(R.drawable.book_icon), contentDescription = "Book icon") },
                                            label = { Text("Books") }
                                        )
                                        NavigationBarItem(
                                            selected = true,
                                            onClick = { navController.navigate("location_list") },
                                            icon = { Icon(painterResource(R.drawable.shelves_icon), contentDescription = "Location icon") },
                                            label = { Text("Locations") }
                                        )
                                        NavigationBarItem(
                                            selected = false,
                                            onClick = {  },
                                            icon = { Icon(Icons.Default.FavoriteBorder, contentDescription = "Favorites icon") },
                                            label = { Text("Favorites") }
                                        )
                                        NavigationBarItem(
                                            selected = false,
                                            onClick = {  },
                                            icon = {  Icon(painterResource(R.drawable.star_icon), contentDescription = "Wishlist icon")  },
                                            label = { Text("Wishlist") }
                                        )
                                    }
                                }
                            ) { it ->
                                Column(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(top = 72.dp, bottom = it.calculateBottomPadding())
                                ) {
                                    LocationListScreen(navController = navController, viewModel = LocationListViewModel(locationDao = db.locationDao()))
                                }
                            }
                        }
                        composable("book_details/{bookId}") {
                            BookDetailsScreen(navController = navController, viewModel = BookDetailsViewModel(selectedBookId = it.arguments?.getString("bookId")!!.toInt(), bookDao = db.bookDao(), locationDao = db.locationDao(), genreDao = db.genreDao()))
                        }
                    }
                }
            }
        }
    }
}
