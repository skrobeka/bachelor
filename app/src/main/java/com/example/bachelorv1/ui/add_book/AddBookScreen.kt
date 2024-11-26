package com.example.bachelorv1.ui.add_book

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
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
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.bachelorv1.R
import com.example.bachelorv1.ui.theme.BachelorV1Theme
import kotlinx.coroutines.flow.stateIn

@Composable
fun AddBookScreen(
    navController: NavController,
    viewModel: AddBookViewModel
) {
    val genresNames = viewModel.getGenresNames()
    val locationsNames = viewModel.getLocationsNames()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 48.dp, start = 16.dp, end = 16.dp, bottom = 16.dp)
    ) {
        /*Top part of the screen*/
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
                        value = viewModel.bookTitle.value,
                        onValueChange = { viewModel.bookTitle.value = it  },
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
                        value = viewModel.authorName.value,
                        onValueChange = { viewModel.authorName.value = it },
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
                            value = viewModel.selectedGenres.joinToString(" | "),
                            onValueChange = {},
                            label = { Text("Genres") },
                            readOnly = true,
                            trailingIcon = { IconButton(onClick = { viewModel.toggleGenreDropdown() }) { Icon(Icons.Filled.KeyboardArrowDown, contentDescription = "Genres list") } },
                        )
                        DropdownMenu(
                            expanded = viewModel.isGenreExpanded.value,
                            onDismissRequest = { viewModel.toggleGenreDropdown() }
                        ) {
                            for (genreName in genresNames) {
                                DropdownMenuItem(
                                    text = {
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            Text(genreName)
                                            Checkbox(
                                                checked = viewModel.selectedGenres.contains(
                                                    genreName
                                                ),
                                                onCheckedChange = {
                                                    viewModel.checkGenreCheckbox(
                                                        genreName
                                                    )
                                                }
                                            )
                                        }
                                    },
                                    onClick = { viewModel.checkGenreCheckbox(genreName) }
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
                            value = viewModel.selectedLocation.value,
                            onValueChange = {},
                            label = { Text("Location") },
                            readOnly = true,
                            trailingIcon = { IconButton(onClick = { viewModel.toggleLocationDropdown() }) { Icon(Icons.Filled.KeyboardArrowDown, contentDescription = "Locations list") } }
                        )
                        DropdownMenu(
                            expanded = viewModel.isLocationExpanded.value,
                            onDismissRequest = { viewModel.toggleLocationDropdown() }
                        ) {
                            for (locationName in locationsNames) {
                                DropdownMenuItem(
                                    text = {
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            RadioButton(
                                                selected = viewModel.selectedLocation.value == locationName,
                                                onClick = { viewModel.checkLocationRadio(locationName) }
                                            )
                                            Text(locationName)
                                        }
                                    },
                                    onClick = { viewModel.checkLocationRadio(locationName) }
                                )
                            }
                        }
                    }
                }
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
                    onClick = { viewModel.saveBookToLibrary()
                                navController.navigate("book_list")
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

