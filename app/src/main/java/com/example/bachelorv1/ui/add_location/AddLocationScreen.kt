package com.example.bachelorv1.ui.add_location

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bachelorv1.MainActivity
import com.example.bachelorv1.R
@Composable
fun AddLocationScreenRoot(
    onBackClick: () -> Unit,
    onLocationSave: () -> Unit
) {
    val viewModel:  AddLocationViewModel = viewModel(factory = object : ViewModelProvider.Factory {
       override fun <T : ViewModel> create(modelClass: Class<T>): T {
           if (modelClass.isAssignableFrom(AddLocationViewModel::class.java))
               return AddLocationViewModel(
                   MainActivity.db.locationDao()
               ) as T
           throw IllegalArgumentException("Unknown ViewModel class")
       }
    })

    val state by viewModel.state.collectAsStateWithLifecycle()

    AddLocationScreen(
        state = state,
        onAction = { action ->
            when (action) {
                is AddLocationAction.OnBackClick -> onBackClick()
                is AddLocationAction.OnLocationSave -> onLocationSave()
                else -> Unit
            }
            viewModel.onAction(action)
        }
    )
}

@Composable
fun AddLocationScreen(
    state: AddLocationState,
    onAction: (AddLocationAction) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 48.dp, start = 16.dp, end = 16.dp, bottom = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .weight(10f),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.LightGray, MaterialTheme.shapes.small),
                value = state.locationName,
                onValueChange = { onAction(AddLocationAction.SetLocationName(it)) },
                label = { Text("Location name") }
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .weight(1f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center

        ) {
            if (state.showError) {
                Text(
                    text = "Please provide location name",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                modifier = Modifier
                    .fillMaxWidth(),
                onClick = {
                    onAction(AddLocationAction.SaveLocation)
                    if (state.locationName.isNotBlank()) {
                        onAction(AddLocationAction.OnLocationSave)
                    }
                }
            ) {
                Icon(painterResource(R.drawable.save_icon), contentDescription = "Save location to your library")
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Save location to your library", style = MaterialTheme.typography.labelMedium)
            }
        }
    }
}
