package com.example.bachelorv1.ui.book_list

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.bachelorv1.MainActivity
import com.example.bachelorv1.R
import com.example.bachelorv1.data.Book

@Composable
fun BookListScreenRoot(
    onBookSelect: (Book) -> Unit
) {
    val bookDao = MainActivity.db.bookDao()
    val viewModel: BookListViewModel = viewModel(factory = object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(BookListViewModel::class.java))
            {
                return BookListViewModel(bookDao)
                as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    })

    val state by viewModel.state.collectAsStateWithLifecycle()

    BookListScreen(
        state = state,
        onAction = { action ->
            when (action) {
                is BookListAction.OnBookSelect -> onBookSelect(action.book)
                else -> Unit
            }
            viewModel.onAction(action)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookListScreen(
    state: BookListState,
    onAction: (BookListAction) -> Unit
) {
    val locationDao = MainActivity.db.locationDao()
    val keyboardController = LocalSoftwareKeyboardController.current

    val searchResultsListState = rememberLazyListState()

    LaunchedEffect(state.searchResults) {
        searchResultsListState.animateScrollToItem(0)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        ) {
            SearchBar(
                query = state.searchQuery,
                onQueryChange = { onAction(BookListAction.OnSearchQueryChange(it)) },
                onSearch = { keyboardController?.hide() },
                placeholder = { Text("Search for a book...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
                trailingIcon = {
                    if (state.searchQuery.isNotBlank()) {
                        IconButton(onClick = { onAction(BookListAction.OnSearchQueryChange("")) }) {
                            Icon(Icons.Filled.Clear, contentDescription = "Clear")
                        }
                    }
                },
                active = false,
                onActiveChange = {},
            ) { }
        }

        LazyColumn {
            val booksToDisplay = if (state.searchQuery.isBlank()) state.books else state.searchResults

            if (state.searchQuery.length > 2 && state.searchResults.isEmpty()) {
                item {
                    Text(
                        text = "No books found",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }

            if (booksToDisplay.isEmpty() && state.searchQuery.isBlank()) {
                item {
                    Text(
                        text = "No books found",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }

            for (book in booksToDisplay) {
                item {
                    Card(
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(192.dp)
                            .padding(bottom = 8.dp),
                        onClick = { onAction(BookListAction.OnBookSelect(book)) }
                    ) {
                        Row {
                            Box {
                                if (book.bookPhoto == null || book.bookPhoto == "") {
                                    Image(
                                        modifier = Modifier
                                            .size(width = 144.dp, height = 192.dp)
                                            .clip(MaterialTheme.shapes.small),
                                        contentScale = ContentScale.FillHeight,
                                        painter = painterResource(R.drawable.test_pic),
                                        contentDescription = "Default book photo"
                                    )
                                }
                                else {
                                    val bookUri = Uri.parse(book.bookPhoto)
                                    AsyncImage(
                                        model = bookUri,
                                        modifier = Modifier
                                            .size(width = 144.dp, height = 192.dp)
                                            .clip(MaterialTheme.shapes.small),
                                        contentScale = ContentScale.FillWidth,
                                        contentDescription = "Book photo"
                                    )
                                }
                                if (book.bookIsFavorite) {
                                    Icon(
                                        imageVector = Icons.Filled.Favorite,
                                        contentDescription = "Favorite",
                                        modifier = Modifier
                                            .align(Alignment.BottomEnd)
                                            .padding(8.dp)
                                            .size(32.dp),
                                        tint = Color.Red
                                    )
                                }
                            }

                            Column(
                                modifier = Modifier
                                    .padding(16.dp)
                                    .fillMaxSize(),
                                verticalArrangement = Arrangement.SpaceEvenly
                            ) {
                                Text(
                                    text = book.bookTitle,
                                    style = MaterialTheme.typography.titleLarge,
                                    maxLines = 2,
                                    overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                                )
                                Text(
                                    text = book.bookAuthor,
                                    style = MaterialTheme.typography.titleMedium,
                                    maxLines = 2,
                                    overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                                )
                                Text(
                                    text = locationDao.getLocationNameById(book.locationId),
                                    style = MaterialTheme.typography.titleSmall,
                                    maxLines = 1,
                                    overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}