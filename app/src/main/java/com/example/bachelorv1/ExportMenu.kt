package com.example.bachelorv1

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.provider.Settings
import android.widget.Toast
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
import java.io.File


@Composable
fun ExportDropdownMenu(context: Context) {
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
                onClick = { exportDatabase(context) }
            )
            DropdownMenuItem(
                text = { Text("Import data") },
                onClick = { importDatabase(context, "import_bookapp.db") }
            )
        }
    }
}

fun exportDatabase(context: Context) {
    val dbFile = context.getDatabasePath("app_database")
    val exportDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)

    val exportFile = File(exportDir, "export" + System.currentTimeMillis() + ".db")

    dbFile.copyTo(exportFile, overwrite = true)

    Toast.makeText(context, "Exported to ${exportFile.absolutePath}", Toast.LENGTH_LONG).show()
}


fun importDatabase(context: Context, fileName: String) {
    val dbFile = context.getDatabasePath("app_database")

    if (Environment.isExternalStorageManager()) {
        val importDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        val importFile = File(importDir, fileName)

        importFile.copyTo(dbFile, overwrite = true)

        Toast.makeText(context, "Imported from ${importFile.absolutePath}", Toast.LENGTH_LONG).show()
    } else {
        val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
        intent.data = Uri.parse("package:" + context.packageName)
        context.startActivity(intent)
    }
}
