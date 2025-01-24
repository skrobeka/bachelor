package com.example.bachelorv1.ui.add_book

import android.net.Uri
import android.util.Log
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.bachelorv1.MainActivity
import com.example.bachelorv1.R
import com.example.bachelorv1.photoPicker
import com.example.bachelorv1.readTextFromPicture

@Composable
fun AddBookScreenRoot(
    onBackClick: () -> Unit,
    onBookSave: () -> Unit
) {
    val viewModel: AddBookViewModel = viewModel(factory = object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AddBookViewModel::class.java))
            {
                return AddBookViewModel(
                    MainActivity.db.bookDao(),
                    MainActivity.db.locationDao(),
                    MainActivity.db.genreDao()
                ) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    })

    val state by viewModel.state.collectAsStateWithLifecycle()

    AddBookScreen(
        state = state,
        onAction = { action ->
            when (action) {
                is AddBookAction.OnBackClick -> onBackClick()
                is AddBookAction.OnBookSave -> onBookSave()
                else -> Unit
            }
            viewModel.onAction(action)
        }
    )
}

@Composable
fun AddBookScreen(
    state: AddBookState,
    onAction: (AddBookAction) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 48.dp, start = 16.dp, end = 16.dp, bottom = 16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            if (state.photo == "") {
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
                AsyncImage(
                    model = state.photo,
                    modifier = Modifier
                        .size(width = 144.dp, height = 192.dp)
                        .clip(MaterialTheme.shapes.small),
                    contentScale = ContentScale.FillWidth,
                    contentDescription = "Book photo"
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        var photo = photoPicker("Add photo")
        var isPhotoSelected = remember { mutableStateOf(false) }

        if (photo != null) {
            onAction(AddBookAction.SetPhoto(photo))
            readTextFromPicture(photo.toString()) { recognizedText ->
                if (state.title.isBlank() && !isPhotoSelected.value) {
                    onAction(AddBookAction.SetTitle(recognizedText))
                }
                if (state.author.isBlank() && !isPhotoSelected.value) {
                    onAction(AddBookAction.SetAuthor(recognizedText))
                }
                isPhotoSelected.value = true
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        TextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.LightGray, MaterialTheme.shapes.small),
                            value = state.title,
                            onValueChange = { onAction(AddBookAction.SetTitle(it)) },
                            label = { Text("Book title") }
                        )
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        TextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.LightGray, MaterialTheme.shapes.small),
                            value = state.author,
                            onValueChange = { onAction(AddBookAction.SetAuthor(it)) },
                            label = { Text("Author's name") }
                        )
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Box {
                            TextField(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Color.LightGray, MaterialTheme.shapes.small),
                                value = state.selectedLocation,
                                onValueChange = {},
                                label = { Text("Location") },
                                readOnly = true,
                                trailingIcon = {
                                    if (state.isLocationExpanded == false) {
                                        IconButton(onClick = {
                                            onAction(
                                                AddBookAction.SetIsLocationExpanded(
                                                    true
                                                )
                                            )
                                        }) {
                                            Icon(
                                                Icons.Filled.KeyboardArrowDown,
                                                contentDescription = "Locations list"
                                            )
                                        }
                                    } else {
                                        IconButton(onClick = {
                                            onAction(
                                                AddBookAction.SetIsLocationExpanded(
                                                    false
                                                )
                                            )
                                        }) {
                                            Icon(
                                                Icons.Filled.KeyboardArrowUp,
                                                contentDescription = "Locations list"
                                            )
                                        }
                                    }
                                },
                                placeholder = { Text("Select location") },
                            )
                            DropdownMenu(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                expanded = state.isLocationExpanded,
                                onDismissRequest = {
                                    onAction(
                                        AddBookAction.SetIsLocationExpanded(
                                            false
                                        )
                                    )
                                }
                            ) {
                                state.locations.forEach { location ->
                                    DropdownMenuItem(
                                        text = {
                                            Row(
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.SpaceBetween
                                            ) {
                                                RadioButton(
                                                    selected = state.selectedLocation == location.locationName,
                                                    onClick = {
                                                        onAction(
                                                            AddBookAction.SetLocation(
                                                                location.locationName
                                                            )
                                                        )
                                                    }
                                                )
                                                Text(location.locationName)
                                            }
                                        },
                                        onClick = { onAction(AddBookAction.SetLocation(location.locationName)) }
                                    )
                                }
                            }
                        }
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Box {
                            TextField(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Color.LightGray, MaterialTheme.shapes.small),
                                value = state.selectedGenres.joinToString(" | "),
                                onValueChange = {},
                                label = { Text("Genres") },
                                readOnly = true,
                                trailingIcon = {
                                    if (state.isGenreExpanded == false) {
                                        IconButton(onClick = {
                                            onAction(
                                                AddBookAction.SetIsGenreExpanded(
                                                    true
                                                )
                                            )
                                        }) {
                                            Icon(
                                                Icons.Filled.KeyboardArrowDown,
                                                contentDescription = "Genres list"
                                            )
                                        }
                                    } else {
                                        IconButton(onClick = {
                                            onAction(
                                                AddBookAction.SetIsGenreExpanded(
                                                    false
                                                )
                                            )
                                        }) {
                                            Icon(
                                                Icons.Filled.KeyboardArrowUp,
                                                contentDescription = "Genres list"
                                            )
                                        }
                                    }
                                },
                                placeholder = { Text("Optional") }
                            )
                            DropdownMenu(
                                expanded = state.isGenreExpanded,
                                onDismissRequest = { onAction(AddBookAction.SetIsGenreExpanded(false)) }
                            ) {
                                state.genres.forEach { genre ->
                                    val updatedGenres = state.selectedGenres.toMutableList()
                                    DropdownMenuItem(
                                        text = {
                                            Row(
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.SpaceBetween
                                            ) {
                                                Text(genre.genreName)
                                                Checkbox(
                                                    checked = state.selectedGenres.contains(genre.genreName),
                                                    onCheckedChange = { isChecked ->
                                                        if (isChecked) {
                                                            updatedGenres.add(genre.genreName)
                                                        } else {
                                                            updatedGenres.remove(genre.genreName)
                                                        }
                                                        onAction(
                                                            AddBookAction.SetGenre(
                                                                updatedGenres
                                                            )
                                                        )
                                                    }
                                                )
                                            }
                                        },
                                        onClick = {
                                            if (!state.selectedGenres.contains(genre.genreName)) {
                                                updatedGenres.add(genre.genreName)
                                            }
                                            else {
                                                updatedGenres.remove(genre.genreName)
                                            }
                                            onAction(AddBookAction.SetGenre(updatedGenres))
                                        }
                                    )
                                }
                            }
                        }
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        TextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.LightGray, MaterialTheme.shapes.small),
                            value = state.note ?: "",
                            onValueChange = { onAction(AddBookAction.SetNote(it)) },
                            label = { Text("Note on book") },
                            placeholder = { Text("Optional") }
                        )
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        TextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.LightGray, MaterialTheme.shapes.small),
                            value = state.cost ?: "",
                            onValueChange = { onAction(AddBookAction.SetCost(it)) },
                            label = { Text("Book's cost") },
                            placeholder = { Text("Optional") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                        )
                    }
                }
            }

            if (state.showError) {
                Text(
                    text = "Please fill in all mandatory fields",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    modifier = Modifier
                        .fillMaxWidth(),
                    onClick = {
                        onAction(AddBookAction.SaveBook)
                        if (state.title.isNotBlank() && state.author.isNotBlank() && state.selectedLocation.isNotBlank()) {
                            onAction(AddBookAction.OnBookSave)
                        }
                    }
                ) {
                    Icon(
                        painterResource(R.drawable.save_icon),
                        contentDescription = "Save book to your library"
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Save book to your library",
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            }
        }
    }
}

