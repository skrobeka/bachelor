package com.example.bachelorv1.ui.location_list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationListScreen(
    navController: NavController,
    viewModel: LocationListViewModel
) {
    val locations = viewModel.locations

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
                placeholder = { Text("Search for a location...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
                active = false,
                onActiveChange = {}
            ) { }
        }

        LazyColumn {
            if (locations.value.isNotEmpty()) {
                for (location in locations.value) {
                    item {
                        Card(
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(96.dp)
                                .padding(bottom = 8.dp),
                            onClick = { /*navController.navigate("location_details/${location.locationId}")*/ }
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(16.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(text = location.locationName, style = MaterialTheme.typography.titleMedium)
                                Text(text = location.locationBookCount.toString(), style = MaterialTheme.typography.titleMedium)
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
                            text = "No locations found",
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                }
            }
        }
    }
}