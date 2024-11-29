package com.example.bachelorv1.ui.edit_book

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bachelorv1.MainActivity
import com.example.bachelorv1.R

@Composable
fun EditBookScreenRoot(
    onBackClick: () -> Unit,
    onBookSave: () -> Unit,
    bookId: Int
) {
    val viewModel: EditBookViewModel = viewModel(factory = object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(EditBookViewModel::class.java))
            {
                return EditBookViewModel(
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

    EditBookScreen(
        state = state,
        onAction = { action ->
            when (action) {
                is EditBookAction.OnBackClick -> onBackClick()
                is EditBookAction.OnBookSave -> onBookSave()
                else -> Unit
            }
            viewModel.onAction(action)
        }
    )
}

@Composable
fun EditBookScreen(
    state: EditBookState,
    onAction: (EditBookAction) -> Unit,
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
            Image(
                modifier = Modifier
                    .size(width = 144.dp, height = 192.dp)
                    .clip(MaterialTheme.shapes.small),
                contentScale = ContentScale.FillHeight,
                painter = painterResource(R.drawable.test_pic),
                contentDescription = null
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
                    .width(144.dp),
                onClick = {/*Add a photo*/ }
            ) {
                Icon(painterResource(R.drawable.add_photo_icon), contentDescription = "Add photo")
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Add photo", style = MaterialTheme.typography.labelMedium)
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
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
                        onValueChange = { onAction(EditBookAction.SetTitle(it)) },
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
                        onValueChange = { onAction(EditBookAction.SetAuthor(it)) },
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
                            value = state.selectedGenres.joinToString(" | "),
                            onValueChange = {},
                            label = { Text("Genres") },
                            readOnly = true,
                            trailingIcon = {
                                if (state.isGenreExpanded == false) {
                                    IconButton(onClick = { onAction(EditBookAction.SetIsGenreExpanded(true)) }) { Icon(Icons.Filled.KeyboardArrowDown, contentDescription = "Genres list") }
                                }
                                else {
                                    IconButton(onClick = { onAction(EditBookAction.SetIsGenreExpanded(false)) }) { Icon(Icons.Filled.KeyboardArrowUp, contentDescription = "Genres list") }
                                }
                            },
                            placeholder = { Text("Select at least one genre") }
                        )
                        DropdownMenu(
                            expanded = state.isGenreExpanded,
                            onDismissRequest = { onAction(EditBookAction.SetIsGenreExpanded(false)) }
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
                                                    onAction(EditBookAction.SetGenre(updatedGenres))
                                                }
                                            )
                                        }
                                    },
                                    onClick = { onAction(EditBookAction.SetGenre(updatedGenres)) }
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
                            value = state.selectedLocation,
                            onValueChange = {},
                            label = { Text("Location") },
                            readOnly = true,
                            trailingIcon = {
                                if (state.isLocationExpanded == false) {
                                    IconButton(onClick = { onAction(EditBookAction.SetIsLocationExpanded(true)) }) { Icon(Icons.Filled.KeyboardArrowDown, contentDescription = "Locations list") }
                                }
                                else {
                                    IconButton(onClick = { onAction(EditBookAction.SetIsLocationExpanded(false)) }) { Icon(Icons.Filled.KeyboardArrowUp, contentDescription = "Locations list") }
                                }
                            },
                            placeholder = { Text("Select location") },
                        )
                        DropdownMenu(
                            expanded = state.isLocationExpanded,
                            onDismissRequest = { onAction(EditBookAction.SetIsLocationExpanded(false)) }
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
                                                onClick = { onAction(EditBookAction.SetLocation(location.locationName)) }
                                            )
                                            Text(location.locationName)
                                        }
                                    },
                                    onClick = { onAction(EditBookAction.SetLocation(location.locationName)) }
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
                        value = state.edition ?: "",
                        onValueChange = { onAction(EditBookAction.SetEdition(it)) },
                        label = { Text("Book's edition") },
                        placeholder = { Text("Optional") }
                    )
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
                        onAction(EditBookAction.SaveBook)
                        if (state.title.isNotBlank() && state.author.isNotBlank() && state.selectedLocation.isNotBlank() && state.selectedGenres.isNotEmpty()) {
                            onAction(EditBookAction.OnBookSave)
                        }
                    }
                ) {
                    Icon(painterResource(R.drawable.save_icon), contentDescription = "Save book to your library")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Save book to your library", style = MaterialTheme.typography.labelMedium)
                }
            }
        }
    }
}