package com.example.bachelorv1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.example.bachelorv1.data.AppDatabase
import com.example.bachelorv1.data.Genre
import com.example.bachelorv1.ui.add_book.AddBookScreenRoot
import com.example.bachelorv1.ui.add_location.AddLocationScreenRoot
import com.example.bachelorv1.ui.book_details.BookDetailsScreenRoot
import com.example.bachelorv1.ui.book_list.BookListScreenRoot
import com.example.bachelorv1.ui.book_list.SelectedBookViewModel
import com.example.bachelorv1.ui.edit_book.EditBookScreenRoot
import com.example.bachelorv1.ui.edit_location.EditLocationScreenRoot
import com.example.bachelorv1.ui.favorite_book_list.FavoriteBookListScreenRoot
import com.example.bachelorv1.ui.location_details.LocationDetailsScreenRoot
import com.example.bachelorv1.ui.location_list.LocationListScreenRoot
import com.example.bachelorv1.ui.location_list.SelectedLocationViewModel
import com.example.bachelorv1.ui.reading_list.ReadingListScreenRoot
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

                    val sharedPreferences = getSharedPreferences("app_prefs", MODE_PRIVATE)

                    LaunchedEffect(Unit) {
                        val isFirstLaunch = sharedPreferences.getBoolean("is_first_launch", true)
                        if (isFirstLaunch) {
                            sharedPreferences.edit().putBoolean("is_first_launch", false).apply()
                            listOf(
                                "Fantasy",
                                "Science fiction",
                                "Distopian",
                                "Action & Adventure",
                                "Mystery",
                                "Horror",
                                "Thriller & Suspense",
                                "Historical fiction",
                                "Romance",
                                "Contemporary fiction",
                                "Literary fiction",
                                "Magical realism",
                                "Graphic novel",
                                "Comics",
                                "Short story",
                                "Young adult",
                                "New adult",
                                "Memoir & Autobiography",
                                "Biography",
                                "Food & Drink",
                                "Art & Photography",
                                "Self-help",
                                "History",
                                "Travel",
                                "True crime",
                                "Humor",
                                "Essays",
                                "Guides/How-to",
                                "Religion & Spirituality",
                                "Humanities & Social sciences",
                                "Parentinig & Families",
                                "Science & Technology",
                                "Children"
                            ).forEach {
                                db.genreDao().insertGenre(Genre(genreName = it))
                            }
                            navController.navigate(Route.AddLocation)
                        }
                    }

                    NavHost(
                        navController = navController,
                        startDestination = Route.BookGraph
                    ) {
                        navigation<Route.BookGraph>(
                            startDestination = Route.BookList,
                        ) {
                            composable<Route.BookList> {
                                val selectedBookViewModel = it.sharedViewModel<SelectedBookViewModel>(navController)

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
                                            actions = { ExportDropdownMenu() },
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
                                            listOf(
                                                Route.BookList,
                                                Route.LocationList,
                                                Route.FavoriteList,
                                                Route.ReadingList
                                            ).forEach { route ->
                                                val title = when (route) {
                                                    Route.BookList -> "Books"
                                                    Route.LocationList -> "Locations"
                                                    Route.FavoriteList -> "Favorites"
                                                    Route.ReadingList -> "To Read list"
                                                    else -> ""
                                                }
                                                NavigationBarItem(
                                                    selected = route == Route.BookList,
                                                    onClick = {
                                                        navController.navigate(route) {
                                                            popUpTo(Route.BookList) { inclusive = true }
                                                        }
                                                    },
                                                    icon = {
                                                        when (route) {
                                                            Route.BookList -> Icon(painterResource(R.drawable.book_icon), contentDescription = "Books icon")
                                                            Route.LocationList -> Icon(painterResource(R.drawable.shelves_icon), contentDescription = "Shelves icon")
                                                            Route.FavoriteList -> Icon(Icons.Default.Favorite, contentDescription = "Favorites icon")
                                                            Route.ReadingList -> Icon(Icons.Default.List, contentDescription = "Reading list icon")
                                                            else -> Icon(Icons.Default.Lock, contentDescription = "?")
                                                        }
                                                    },
                                                    label = { Text(title) }
                                                )
                                            }
                                        }
                                    },
                                    floatingActionButton = {
                                        ExtendedFloatingActionButton(
                                            text = { Text("Add book") },
                                            icon = { Icon(Icons.Default.Add, contentDescription = "Add book") },
                                            onClick = { navController.navigate(Route.AddBook) },
                                            backgroundColor = MaterialTheme.colorScheme.secondaryContainer,
                                            contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                                        )
                                    }
                                ) { innerPadding ->
                                    Column(
                                        modifier = Modifier.padding(top = 72.dp, bottom = innerPadding.calculateBottomPadding())
                                    ) {
                                        BookListScreenRoot(
                                            onBookSelect = { book ->
                                                selectedBookViewModel.onBookSelect(book)
                                                navController.navigate(Route.BookDetails(book.bookId))
                                            }
                                        )
                                    }
                                }
                            }

                            composable<Route.BookDetails> {
                                Scaffold(
                                    topBar = {
                                        CenterAlignedTopAppBar(
                                            title = { Text("Book details") },
                                            navigationIcon = {
                                                IconButton(onClick = { navController.popBackStack() }) {
                                                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
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
                                    }
                                ) { innerPadding ->
                                    Column(
                                        modifier = Modifier.padding(top = innerPadding.calculateTopPadding())
                                    ) {
                                        BookDetailsScreenRoot(
                                            onBackClick = { navController.popBackStack() },
                                            onEditClick = { navController.navigate(Route.EditBook(it.arguments?.getInt("bookId") ?: -1)) },
                                            onDeleteClick = { navController.navigate(Route.BookList) { popUpTo(Route.BookDetails(it.arguments?.getInt("bookId") ?: -1)) { inclusive = true } } },
                                            bookId = it.arguments?.getInt("bookId") ?: -1
                                        )
                                    }
                                }
                            }

                            composable<Route.FavoriteList> {
                                val selectedBookViewModel = it.sharedViewModel<SelectedBookViewModel>(navController)

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
                                            actions = { ExportDropdownMenu() },
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
                                            listOf(
                                                Route.BookList,
                                                Route.LocationList,
                                                Route.FavoriteList,
                                                Route.ReadingList
                                            ).forEach { route ->
                                                val title = when (route) {
                                                    Route.BookList -> "Books"
                                                    Route.LocationList -> "Locations"
                                                    Route.FavoriteList -> "Favorites"
                                                    Route.ReadingList -> "To Read list"
                                                    else -> ""
                                                }
                                                NavigationBarItem(
                                                    selected = route == Route.FavoriteList,
                                                    onClick = {
                                                        navController.navigate(route) {
                                                            popUpTo(Route.FavoriteList) { inclusive = true }
                                                        }
                                                    },
                                                    icon = {
                                                        when (route) {
                                                            Route.BookList -> Icon(painterResource(R.drawable.book_icon), contentDescription = "Books icon")
                                                            Route.LocationList -> Icon(painterResource(R.drawable.shelves_icon), contentDescription = "Shelves icon")
                                                            Route.FavoriteList -> Icon(Icons.Default.Favorite, contentDescription = "Favorites icon")
                                                            Route.ReadingList -> Icon(Icons.Default.List, contentDescription = "Reading list icon")
                                                            else -> Icon(Icons.Default.Lock, contentDescription = "?")
                                                        }
                                                    },
                                                    label = { Text(title) }
                                                )
                                            }
                                        }
                                    },
                                    floatingActionButton = {
                                        ExtendedFloatingActionButton(
                                            text = { Text("Add book") },
                                            icon = { Icon(Icons.Default.Add, contentDescription = "Add book") },
                                            onClick = { navController.navigate(Route.AddBook) },
                                            backgroundColor = MaterialTheme.colorScheme.secondaryContainer,
                                            contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                                        )
                                    }
                                ) { innerPadding ->
                                    Column(
                                        modifier = Modifier.padding(top = 72.dp, bottom = innerPadding.calculateBottomPadding())
                                    ) {
                                        FavoriteBookListScreenRoot(
                                            onBookSelect = { book ->
                                                selectedBookViewModel.onBookSelect(book)
                                                navController.navigate(Route.BookDetails(book.bookId))
                                            }
                                        )
                                    }
                                }
                            }

                            composable<Route.LocationList> {
                                val selectedLocationViewModel = it.sharedViewModel<SelectedLocationViewModel>(navController)

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
                                            actions = { ExportDropdownMenu() },
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
                                            listOf(
                                                Route.BookList,
                                                Route.LocationList,
                                                Route.FavoriteList,
                                                Route.ReadingList
                                            ).forEach { route ->
                                                val title = when (route) {
                                                    Route.BookList -> "Books"
                                                    Route.LocationList -> "Locations"
                                                    Route.FavoriteList -> "Favorites"
                                                    Route.ReadingList -> "To Read list"
                                                    else -> ""
                                                }
                                                NavigationBarItem(
                                                    selected = route == Route.LocationList,
                                                    onClick = {
                                                        navController.navigate(route) {
                                                            popUpTo(Route.LocationList) { inclusive = true }
                                                        }
                                                    },
                                                    icon = {
                                                        when (route) {
                                                            Route.BookList -> Icon(painterResource(R.drawable.book_icon), contentDescription = "Books icon")
                                                            Route.LocationList -> Icon(painterResource(R.drawable.shelves_icon), contentDescription = "Shelves icon")
                                                            Route.FavoriteList -> Icon(Icons.Default.Favorite, contentDescription = "Favorites icon")
                                                            Route.ReadingList -> Icon(Icons.Default.List, contentDescription = "Reading list icon")
                                                            else -> Icon(Icons.Default.Lock, contentDescription = "?")
                                                        }
                                                    },
                                                    label = { Text(title) }
                                                )
                                            }
                                        }
                                    },
                                    floatingActionButton = {
                                        ExtendedFloatingActionButton(
                                            text = { Text("Add location") },
                                            icon = { Icon(Icons.Default.Add, contentDescription = "Add location") },
                                            onClick = { navController.navigate(Route.AddLocation) },
                                            backgroundColor = MaterialTheme.colorScheme.secondaryContainer,
                                            contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                                        )
                                    }
                                ) { innerPadding ->
                                    Column(
                                        modifier = Modifier.padding(top = 72.dp, bottom = innerPadding.calculateBottomPadding())
                                    ) {
                                        LocationListScreenRoot(
                                            onLocationSelect = { location ->
                                                selectedLocationViewModel.onLocationSelect(location)
                                                navController.navigate(Route.LocationDetails(location.locationId))
                                            }
                                        )
                                    }
                                }
                            }

                            composable<Route.ReadingList> {
                                val selectedBookViewModel = it.sharedViewModel<SelectedBookViewModel>(navController)

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
                                            actions = { ExportDropdownMenu() },
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
                                            listOf(
                                                Route.BookList,
                                                Route.LocationList,
                                                Route.FavoriteList,
                                                Route.ReadingList
                                            ).forEach { route ->
                                                val title = when (route) {
                                                    Route.BookList -> "Books"
                                                    Route.LocationList -> "Locations"
                                                    Route.FavoriteList -> "Favorites"
                                                    Route.ReadingList -> "To Read list"
                                                    else -> ""
                                                }
                                                NavigationBarItem(
                                                    selected = route == Route.ReadingList,
                                                    onClick = {
                                                        navController.navigate(route) {
                                                            popUpTo(Route.ReadingList) { inclusive = true }
                                                        }
                                                    },
                                                    icon = {
                                                        when (route) {
                                                            Route.BookList -> Icon(painterResource(R.drawable.book_icon), contentDescription = "Books icon")
                                                            Route.LocationList -> Icon(painterResource(R.drawable.shelves_icon), contentDescription = "Shelves icon")
                                                            Route.FavoriteList -> Icon(Icons.Default.Favorite, contentDescription = "Favorites icon")
                                                            Route.ReadingList -> Icon(Icons.Default.List, contentDescription = "Reading list icon")
                                                            else -> Icon(Icons.Default.Lock, contentDescription = "?")
                                                        }
                                                    },
                                                    label = { Text(title) }
                                                )
                                            }
                                        }
                                    }
                                ) { innerPadding ->
                                    Column(
                                        modifier = Modifier.padding(top = 72.dp, bottom = innerPadding.calculateBottomPadding())
                                    ) {
                                        ReadingListScreenRoot(
                                            onBookSelect = { book ->
                                                selectedBookViewModel.onBookSelect(book)
                                                navController.navigate(Route.BookDetails(book.bookId))
                                            }
                                        )
                                    }
                                }
                            }

                            composable<Route.LocationDetails> {
                                val locationName = db.locationDao().getLocationNameById(it.arguments?.getInt("locationId") ?: -1)

                                Scaffold(
                                    topBar = {
                                        CenterAlignedTopAppBar(
                                            title = { Text(locationName) },
                                            navigationIcon = {
                                                IconButton(onClick = { navController.popBackStack() }) {
                                                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                                                }
                                            },
                                            actions = {
                                                IconButton(onClick = { navController.navigate(Route.EditLocation(it.arguments?.getInt("locationId") ?: -1)) }) {
                                                    Icon(Icons.Default.Edit, contentDescription = "Edit location")
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
                                    floatingActionButton = {
                                        ExtendedFloatingActionButton(
                                            text = { Text("Add book") },
                                            icon = { Icon(Icons.Default.Add, contentDescription = "Add book") },
                                            onClick = { navController.navigate(Route.AddBook) },
                                            backgroundColor = MaterialTheme.colorScheme.secondaryContainer,
                                            contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                                        )
                                    }
                                ) { innerPadding ->
                                    Column(
                                        modifier = Modifier.padding(top = 72.dp, bottom = innerPadding.calculateBottomPadding())
                                    ) {
                                        LocationDetailsScreenRoot(
                                            onBookSelect = { book ->
                                                navController.navigate(Route.BookDetails(book.bookId))
                                            },
                                            locationId = it.arguments?.getInt("locationId") ?: -1
                                        )
                                    }
                                }
                            }

                            composable<Route.AddLocation> {
                                Scaffold(
                                    topBar = {
                                        CenterAlignedTopAppBar(
                                            title = { Text("Add location") },
                                            navigationIcon = {
                                                IconButton(onClick = { navController.popBackStack() }) {
                                                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
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
                                    }
                                ) { innerPadding ->
                                    Column(
                                        modifier = Modifier.padding(top = 72.dp, bottom = innerPadding.calculateBottomPadding())
                                    ) {
                                        AddLocationScreenRoot(
                                            onBackClick = { navController.popBackStack() },
                                            onLocationSave = { navController.navigate(Route.LocationList) { popUpTo(Route.AddLocation) {inclusive = true} } }
                                        )
                                    }
                                }
                            }

                            composable<Route.AddBook> {
                                Scaffold(
                                    topBar = {
                                        CenterAlignedTopAppBar(
                                            title = { Text("Add book") },
                                            navigationIcon = {
                                                IconButton(onClick = { navController.popBackStack() }) {
                                                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
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
                                    }
                                ) { innerPadding ->
                                    Column(
                                        modifier = Modifier.padding(top = 72.dp, bottom = innerPadding.calculateBottomPadding())
                                    ) {
                                        AddBookScreenRoot(
                                            onBackClick = { navController.popBackStack() },
                                            onBookSave = { navController.navigate(Route.BookList) { popUpTo(Route.AddBook) {inclusive = true} } }
                                        )
                                    }
                                }
                            }

                            composable<Route.EditBook> {
                                Scaffold(
                                    topBar = {
                                        CenterAlignedTopAppBar(
                                            title = { Text("Edit book") },
                                            navigationIcon = {
                                                IconButton(onClick = { navController.popBackStack() }) {
                                                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
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
                                    }
                                ) { innerPadding ->
                                    Column(
                                        modifier = Modifier.padding(top = 72.dp, bottom = innerPadding.calculateBottomPadding())
                                    ) {
                                        EditBookScreenRoot(
                                            onBackClick = { navController.popBackStack() },
                                            onBookSave = { navController.navigate(Route.BookDetails(it.arguments?.getInt("bookId") ?: -1)) {
                                                navController.popBackStack()
                                                navController.popBackStack()
                                            } },
                                            bookId = it.arguments?.getInt("bookId") ?: -1
                                        )
                                    }
                                }
                            }

                            composable<Route.EditLocation> {
                                Scaffold(
                                    topBar = {
                                        CenterAlignedTopAppBar(
                                            title = { Text("Edit location") },
                                            navigationIcon = {
                                                IconButton(onClick = { navController.popBackStack() }) {
                                                    Icon(
                                                        Icons.Default.ArrowBack,
                                                        contentDescription = "Back"
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
                                    }
                                ) { innerPadding ->
                                    Column(
                                        modifier = Modifier.padding(
                                            top = 72.dp,
                                            bottom = innerPadding.calculateBottomPadding()
                                        )
                                    ) {
                                        EditLocationScreenRoot(
                                            onBackClick = { navController.popBackStack() },
                                            onLocationSave = { navController.navigate(Route.LocationList) { popUpTo(Route.EditLocation(it.arguments?.getInt("locationId") ?: -1)) { inclusive = true } } },
                                            onDeleteClick = { navController.navigate(Route.LocationList) { popUpTo(Route.LocationDetails(it.arguments?.getInt("locationId") ?: -1)) { inclusive = true } } },
                                            locationId = it.arguments?.getInt("locationId") ?: -1
                                        )
                                    }
                                }
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
