package com.example.bachelorv1

import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

@Composable
fun ExportDropdownMenu() {
    var expanded  = remember { mutableStateOf(false) }

    Box {
        IconButton(onClick = { expanded.value = !expanded.value }) {
            Icon(Icons.Default.MoreVert, contentDescription = "More options")
        }
        DropdownMenu(
            expanded = expanded.value,
            onDismissRequest = { expanded.value = false }
        ) {
            DropdownMenuItem(
                text = { Text("Export data") },
                onClick = { /* Do something... */ }
            )
            DropdownMenuItem(
                text = { Text("Import data") },
                onClick = { /* Do something... */ }
            )
        }
    }
}
