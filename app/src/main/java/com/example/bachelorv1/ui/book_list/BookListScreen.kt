package com.example.bachelorv1.ui.book_list

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.bachelorv1.R
import com.example.bachelorv1.ui.add_book.AddBookScreen
import com.example.bachelorv1.ui.theme.BachelorV1Theme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookListScreen(
    navController: NavController,
    viewModel: BookListViewModel
) {
    val books = viewModel.books

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
                query = viewModel.searchQuery.value,
                onQueryChange = { viewModel.onQueryChange(it) },
                onSearch = { viewModel.onSearch() },
                placeholder = { Text("Search for a book...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
                active = false,
                onActiveChange = {},
            ) { }
        }

        LazyColumn {
            if (books.value.isNotEmpty()) {
                for (book in books.value) {
                    item {
                        Card(
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(192.dp)
                                .padding(bottom = 8.dp),
                            onClick = { navController.navigate("book_details/${book.bookId}") }
                        ) {
                            Row {
                                Image(
                                    modifier = Modifier
                                        .size(width = 144.dp, height = 192.dp)
                                        .clip(MaterialTheme.shapes.small),
                                    contentScale = ContentScale.FillHeight,
                                    painter = painterResource(R.drawable.test_pic),
                                    contentDescription = null
                                )

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
                                        style = MaterialTheme.typography.titleMedium
                                    )
                                    Text(
                                        text = viewModel.getLocationNameById(book.locationId),
                                        style = MaterialTheme.typography.titleSmall
                                    )
                                }
                            }
                        }
                    }
                }
            }

            else {
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "No books found",
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                }
            }
        }
    }
}