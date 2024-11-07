package com.example.bachelorv1.ui.book_list

import androidx.compose.foundation.Image
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
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
    //navController: NavController
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 32.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        ) { SearchBar(
            query = "",
            onQueryChange = { },
            onSearch = { },
            placeholder = { Text("Search") },
            active = false,
            onActiveChange = { }
            ) {
            LazyColumn {
                item {
                    Text("test")
                    }
                }
            }
        }

        LazyColumn {
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(192.dp)
                        .padding(bottom = 8.dp)
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
                            Text(text = "Book title", style = MaterialTheme.typography.titleLarge, maxLines = 2, overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis)
                            Text(text = "Author name", style = MaterialTheme.typography.titleMedium)
                            Text(text = "Location", style = MaterialTheme.typography.titleSmall)
                        }
                    }
                }
            }

            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(192.dp)
                        .padding(bottom = 8.dp)
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
                            Text(text = "Book title", style = MaterialTheme.typography.titleLarge, maxLines = 2, overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis)
                            Text(text = "Author name", style = MaterialTheme.typography.titleMedium)
                            Text(text = "Location", style = MaterialTheme.typography.titleSmall)
                        }
                    }
                }
            }

            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(192.dp)
                        .padding(bottom = 8.dp)
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
                            Text(text = "Book title", style = MaterialTheme.typography.titleLarge, maxLines = 2, overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis)
                            Text(text = "Author name", style = MaterialTheme.typography.titleMedium)
                            Text(text = "Location", style = MaterialTheme.typography.titleSmall)
                        }
                    }
                }
            }

            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(192.dp)
                        .padding(bottom = 8.dp)
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
                            Text(text = "Book title", style = MaterialTheme.typography.titleLarge, maxLines = 2, overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis)
                            Text(text = "Author name", style = MaterialTheme.typography.titleMedium)
                            Text(text = "Location", style = MaterialTheme.typography.titleSmall)
                        }
                    }
                }
            }

            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(192.dp)
                        .padding(bottom = 8.dp)
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
                            Text(text = "Book title", style = MaterialTheme.typography.titleLarge, maxLines = 2, overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis)
                            Text(text = "Author name", style = MaterialTheme.typography.titleMedium)
                            Text(text = "Location", style = MaterialTheme.typography.titleSmall)
                        }
                    }
                }
            }

            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(192.dp)
                        .padding(bottom = 8.dp)
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
                            Text(text = "Book title", style = MaterialTheme.typography.titleLarge, maxLines = 2, overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis)
                            Text(text = "Author name", style = MaterialTheme.typography.titleMedium)
                            Text(text = "Location", style = MaterialTheme.typography.titleSmall)
                        }
                    }
                }
            }

            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(192.dp)
                        .padding(bottom = 8.dp)
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
                            Text(text = "Book title", style = MaterialTheme.typography.titleLarge, maxLines = 2, overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis)
                            Text(text = "Author name", style = MaterialTheme.typography.titleMedium)
                            Text(text = "Location", style = MaterialTheme.typography.titleSmall)
                        }
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    BachelorV1Theme {
        BookListScreen()
    }
}