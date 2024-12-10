package com.example.bachelorv1.ui.location_list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bachelorv1.MainActivity
import com.example.bachelorv1.data.Location
import com.example.bachelorv1.ui.book_list.BookListViewModel

@Composable
fun LocationListScreenRoot(
    onLocationSelect: (Location) -> Unit
) {
    val locationDao = MainActivity.db.locationDao()
    val viewModel: LocationListViewModel = viewModel(factory = object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(LocationListViewModel::class.java))
            {
                return LocationListViewModel(locationDao)
                        as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    })

    val state by viewModel.state.collectAsStateWithLifecycle()

    LocationListScreen(
        state = state,
        onAction = { action ->
            when (action) {
                is LocationListAction.OnLocationSelect -> onLocationSelect(action.location)
                else -> Unit
            }
            viewModel.onAction(action)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationListScreen(
    state: LocationListState,
    onAction: (LocationListAction) -> Unit
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
                onQueryChange = { onAction(LocationListAction.OnSearchQueryChange(it)) },
                onSearch = { keyboardController?.hide() },
                placeholder = { Text("Search for a location...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
                trailingIcon = {
                    if (state.searchQuery.isNotBlank()) {
                        IconButton(onClick = { onAction(LocationListAction.OnSearchQueryChange("")) }) {
                            Icon(Icons.Filled.Clear, contentDescription = "Clear")
                        }
                    }
                },
                active = false,
                onActiveChange = {}
            ) { }
        }

        LazyColumn {
            val locationsToDisplay = if (state.searchQuery.isBlank()) state.locations else state.searchResults

            if (state.searchQuery.length > 2 && state.searchResults.isEmpty()) {
                item {
                    Text(
                        text = "No locations found",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }

            if (locationsToDisplay.isEmpty()) {
                item {
                    Text(
                        text = "No locations found",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }

            for (location in locationsToDisplay) {
                item {
                    Card(
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(96.dp)
                            .padding(bottom = 8.dp),
                        onClick = { onAction(LocationListAction.OnLocationSelect(location)) }
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = location.locationName, style = MaterialTheme.typography.titleLarge)
                            Text(text = "Book count: " + locationDao.getLocationBookCount(location.locationId), style = MaterialTheme.typography.titleLarge)
                        }
                    }
                }
            }
        }
    }
}