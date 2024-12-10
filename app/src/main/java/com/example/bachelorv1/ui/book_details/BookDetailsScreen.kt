package com.example.bachelorv1.ui.book_details

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bachelorv1.MainActivity
import com.example.bachelorv1.R

@Composable
fun BookDetailsScreenRoot(
    onBackClick: () -> Unit,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
    bookId: Int
) {
    val viewModel: BookDetailsViewModel = viewModel(factory = object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(BookDetailsViewModel::class.java))
            {
                return BookDetailsViewModel(
                    bookId,
                    MainActivity.db.bookDao(),
                    MainActivity.db.locationDao(),
                    MainActivity.db.genreDao()
                ) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    })

    val state by viewModel.state.collectAsStateWithLifecycle()

    BookDetailsScreen(
        state = state,
        onAction = { action ->
            when (action) {
                is BookDetailsAction.OnBackClick -> onBackClick()
                is BookDetailsAction.OnEditClick -> onEditClick()
                is BookDetailsAction.OnDeleteClick -> onDeleteClick()
                else -> Unit
            }
            viewModel.onAction(action)
        }
    )
}

@Composable
fun BookDetailsScreen(
    state: BookDetailsState,
    onAction: (BookDetailsAction) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box {
                Image(
                    modifier = Modifier
                        .size(width = 144.dp, height = 192.dp)
                        .clip(MaterialTheme.shapes.small),
                    contentScale = ContentScale.FillHeight,
                    painter = painterResource(R.drawable.test_pic),
                    contentDescription = null
                )
                if (state.isFavorite) {
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

            Spacer(modifier = Modifier.width(32.dp))

            Column(
                modifier = Modifier
                    .height(192.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Bottom
            ) {
                Column {
                    Text(text = state.book?.bookTitle.toString(), style = MaterialTheme.typography.titleLarge, maxLines = 2, overflow = TextOverflow.Ellipsis)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = state.book?.bookAuthor.toString(), style = MaterialTheme.typography.titleMedium)
                }

                Spacer(modifier = Modifier.height(32.dp))

                if(state.isFavorite) {
                    Button(
                        modifier = Modifier
                            .fillMaxWidth(),
                        onClick = { onAction(BookDetailsAction.OnFavoriteClick) }
                    ) {
                        Icon(Icons.Default.Favorite, contentDescription = "Remove from favorites")
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = "Remove from favorites", style = MaterialTheme.typography.labelMedium)
                    }
                }
                else {
                    Button(
                        modifier = Modifier
                            .fillMaxWidth(),
                        onClick = { onAction(BookDetailsAction.OnFavoriteClick) }
                    ) {
                        Icon(Icons.Default.FavoriteBorder, contentDescription = "Add to favorites")
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = "Add to favorites", style = MaterialTheme.typography.labelMedium)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            LazyColumn(
                modifier = Modifier
                    .background(
                        MaterialTheme.colorScheme.secondaryContainer,
                        MaterialTheme.shapes.small
                    )
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
                        Icon(painterResource(R.drawable.book_icon), contentDescription = "Book icon", tint = MaterialTheme.colorScheme.onSecondaryContainer)
                        Spacer(modifier = Modifier.width(16.dp))
                        Column {
                            Text(text = "Title", color = MaterialTheme.colorScheme.onSecondaryContainer, style = MaterialTheme.typography.bodyMedium)
                            Text(text = state.book?.bookTitle.toString(), style = MaterialTheme.typography.titleMedium, maxLines = 1, overflow = TextOverflow.Ellipsis)
                        }
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(painterResource(R.drawable.author_icon), contentDescription = "Author icon", tint = MaterialTheme.colorScheme.onSecondaryContainer)
                        Spacer(modifier = Modifier.width(16.dp))
                        Column {
                            Text(text = "Author", style = MaterialTheme.typography.bodyMedium)
                            Text(text = state.book?.bookAuthor.toString(), style = MaterialTheme.typography.titleMedium)
                        }
                    }


                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(painterResource(R.drawable.genres_icon), contentDescription = "Author icon", tint = MaterialTheme.colorScheme.onSecondaryContainer)
                        Spacer(modifier = Modifier.width(16.dp))
                        Column {
                            Text(text = "Genre", color = MaterialTheme.colorScheme.onSecondaryContainer, style = MaterialTheme.typography.bodyMedium)
                            LazyRow { item{Text(text = state.bookGenres.joinToString(" | "), color = MaterialTheme.colorScheme.onSecondaryContainer, style = MaterialTheme.typography.titleMedium, maxLines = 1, overflow = TextOverflow.Ellipsis) } }
                        }
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(painterResource(R.drawable.shelves_icon), contentDescription = "Shelves icon", tint = MaterialTheme.colorScheme.onSecondaryContainer)
                        Spacer(modifier = Modifier.width(16.dp))
                        Column {
                            Text(text = "Location", color = MaterialTheme.colorScheme.onSecondaryContainer, style = MaterialTheme.typography.bodyMedium, softWrap = false)
                            Text(text = state.bookLocation, color = MaterialTheme.colorScheme.onSecondaryContainer, style = MaterialTheme.typography.titleMedium, maxLines = 1, overflow = TextOverflow.Ellipsis)
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
                            Icon(painterResource(R.drawable.isread_icon), contentDescription = "Is read icon", tint = MaterialTheme.colorScheme.onSecondaryContainer)
                            Spacer(modifier = Modifier.width(16.dp))
                            Column {
                                Text(text = "Reading status", color = MaterialTheme.colorScheme.onSecondaryContainer, style = MaterialTheme.typography.bodyMedium)
                                if(state.isRead) {
                                    Text(text = "Read", color = MaterialTheme.colorScheme.onSecondaryContainer, style = MaterialTheme.typography.titleMedium)
                                }
                                else {
                                    Text(text = "Not read", color = MaterialTheme.colorScheme.onSecondaryContainer, style = MaterialTheme.typography.titleMedium)
                                }
                            }
                        }
                        Switch(
                            modifier = Modifier
                                .padding(start = 16.dp),
                            checked = state.isRead,
                            onCheckedChange = { onAction(BookDetailsAction.OnIsReadClick) }
                        )
                    }

                    if (state.book?.bookEdition != null && state.book.bookEdition != "") {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(painterResource(R.drawable.edition_icon), contentDescription = "Edition icon", tint = MaterialTheme.colorScheme.onSecondaryContainer)
                            Spacer(modifier = Modifier.width(16.dp))
                            Column {
                                Text(text = "Book edition", color = MaterialTheme.colorScheme.onSecondaryContainer, style = MaterialTheme.typography.bodyMedium)
                                Text(text = state.book.bookEdition, color = MaterialTheme.colorScheme.onSecondaryContainer, style = MaterialTheme.typography.titleMedium)
                            }
                        }
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(painterResource(R.drawable.date_added_icon), contentDescription = "Date added icon", tint = MaterialTheme.colorScheme.onSecondaryContainer)
                        Spacer(modifier = Modifier.width(16.dp))
                        Column {
                            Text(text = "Date added", color = MaterialTheme.colorScheme.onSecondaryContainer, style = MaterialTheme.typography.bodyMedium)
                            Text(text = state.book?.bookAddedDate.toString(), color = MaterialTheme.colorScheme.onSecondaryContainer, style = MaterialTheme.typography.titleMedium)
                        }
                    }

                    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.Search, contentDescription = "Search icon", tint = MaterialTheme.colorScheme.onSecondaryContainer)
                        Spacer(modifier = Modifier.width(16.dp))
                        Column {
                            Text(text = "Search in web", color = MaterialTheme.colorScheme.onSecondaryContainer, style = MaterialTheme.typography.bodyMedium)
                            val bookLink = state.book?.bookTitle?.replace(" ", "+") + "+" + state.book?.bookAuthor?.replace(" ", "+")
                            Row(modifier = Modifier.clickable {
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.pl/search?q=$bookLink"))
                                launcher.launch(intent)
                            }) {
                                Text(text = "https://www.google.pl/search?q=$bookLink",
                                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                                    style = MaterialTheme.typography.titleMedium,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    modifier = Modifier
                        .weight(1f),
                    onClick = { onAction(BookDetailsAction.OnEditClick) }
                ) {
                    Icon(Icons.Default.Edit, contentDescription = "Edit book")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Edit book", style = MaterialTheme.typography.labelMedium)
                }

                Spacer(modifier = Modifier.width(32.dp))

                Button(
                    modifier = Modifier
                        .weight(1f),
                    onClick = { onAction(BookDetailsAction.OnDeleteClick) }
                ) {
                    Icon(Icons.Default.Delete, contentDescription = "Delete book")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Delete book", style = MaterialTheme.typography.labelMedium)
                }
            }
        }
    }
}
