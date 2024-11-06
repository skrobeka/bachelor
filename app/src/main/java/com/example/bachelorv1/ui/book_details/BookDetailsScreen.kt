package com.example.bachelorv1.ui.book_details

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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bachelorv1.R
import com.example.bachelorv1.ui.theme.BachelorV1Theme

@Composable
fun BookDetailsScreen(
    //navController: NavController,
    //book: Book?
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 32.dp)
    ) {
        /*Top part of the screen*/

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier
                    .size(width = 128.dp, height = 192.dp)
                    .clip(MaterialTheme.shapes.small),
                contentScale = ContentScale.FillHeight,
                painter = painterResource(R.drawable.test_pic),
                contentDescription = null
            )

            Spacer(modifier = Modifier.width(32.dp))

            Column(
                modifier = Modifier
                    .height(192.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Bottom
            ) {
                Column {
                    Text(text = "Book title", style = MaterialTheme.typography.titleLarge, maxLines = 2, overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Author name", style = MaterialTheme.typography.titleMedium)
                }

                Spacer(modifier = Modifier.height(32.dp))

                Button(
                    modifier = Modifier
                        .fillMaxWidth(),
                    onClick = {/*Add to favorites*/}
                ) {
                    Icon(Icons.Default.FavoriteBorder, contentDescription = "Add to favorites")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Add to favorites", style = MaterialTheme.typography.labelMedium)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        /*Middle part of the screen*/
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            LazyColumn(
                modifier = Modifier
                    .background(Color.LightGray, MaterialTheme.shapes.small)
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                item{
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(painterResource(R.drawable.book_icon), contentDescription = "Book icon")
                        Spacer(modifier = Modifier.width(16.dp))
                        Column {
                            Text(text = "Title", style = MaterialTheme.typography.bodyMedium)
                            Text(text = "Book title", style = MaterialTheme.typography.titleMedium, maxLines = 1, overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis)
                        }
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(painterResource(R.drawable.author_icon), contentDescription = "Author icon")
                        Spacer(modifier = Modifier.width(16.dp))
                        Column {
                            Text(text = "Author", style = MaterialTheme.typography.bodyMedium)
                            Text(text = "Author name", style = MaterialTheme.typography.titleMedium)
                        }
                    }

                    val genres = listOf("Genre 1", "Genre 2", "Genre 3", "Genre 4", "Genre 5", "Genre 6")
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(painterResource(R.drawable.genres_icon), contentDescription = "Author icon")
                        Spacer(modifier = Modifier.width(16.dp))
                        Column {
                            Text(text = "Genre", style = MaterialTheme.typography.bodyMedium)
                            LazyRow { item{Text(text = genres.joinToString(" | "), style = MaterialTheme.typography.titleMedium) } }
                        }
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(painterResource(R.drawable.shelves_icon), contentDescription = "Shelves icon")
                        Spacer(modifier = Modifier.width(16.dp))
                        Column {
                            Text(text = "Location", style = MaterialTheme.typography.bodyMedium, softWrap = false)
                            Text(text = "Location name", style = MaterialTheme.typography.titleMedium, maxLines = 1, overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis)
                        }
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            modifier = Modifier
                                .weight(1f),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(painterResource(R.drawable.isread_icon), contentDescription = "Is read icon")
                            Spacer(modifier = Modifier.width(16.dp))
                            Column {
                                Text(text = "Reading status", style = MaterialTheme.typography.bodyMedium)
                                Text(text = "Read", style = MaterialTheme.typography.titleMedium)
                            }
                        }
                        Switch(
                            modifier = Modifier
                                .padding(start = 16.dp),
                            checked = false,
                            onCheckedChange = { /*Change reading status*/ }
                        )
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(painterResource(R.drawable.date_added_icon), contentDescription = "Date added icon")
                        Spacer(modifier = Modifier.width(16.dp))
                        Column {
                            Text(text = "Date added", style = MaterialTheme.typography.bodyMedium)
                            Text(text = "01.01.2024", style = MaterialTheme.typography.titleMedium)
                        }
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.Search, contentDescription = "Search icon")
                        Spacer(modifier = Modifier.width(16.dp))
                        Column {
                            Text(text = "Search in web", style = MaterialTheme.typography.bodyMedium)
                            Row(modifier = Modifier.clickable { /*Open search in web*/ }) {
                                Text(text = "https://www.google.pl/search?q=book+title", style = MaterialTheme.typography.titleMedium, maxLines = 1, overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis,
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            /*Bottom part of the screen*/
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    modifier = Modifier
                        .weight(1f),
                    onClick = {/*Add a photo*/ }
                ) {
                    Icon(painterResource(R.drawable.add_photo_icon), contentDescription = "Add photo")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Add photo", style = MaterialTheme.typography.labelMedium)
                }

                Spacer(modifier = Modifier.width(32.dp))

                Button(
                    modifier = Modifier
                        .weight(1f),
                    onClick = {/*Delete book*/ }
                ) {
                    Icon(Icons.Default.Delete, contentDescription = "Delete book")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Delete book", style = MaterialTheme.typography.labelMedium)
                }
            }
        }
    }
}




@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    BachelorV1Theme {
        BookDetailsScreen()
    }
}
