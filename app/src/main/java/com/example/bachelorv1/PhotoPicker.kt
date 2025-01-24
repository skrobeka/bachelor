package com.example.bachelorv1

import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.MediaStore
import android.provider.OpenableColumns
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.io.File

@Composable
fun photoPicker(label: String): String? {
    val context = LocalContext.current
    var uri = remember { mutableStateOf<Uri?>(null) }
    var cameraPhotoUri = remember { mutableStateOf<Uri?>(null) }
    var filePath = remember { mutableStateOf<String?>(null) }

    val cameraActivity = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                cameraPhotoUri.value?.let { uri ->
                    val copiedFile = copyFileToInternalStorage(context, uri)
                    filePath.value = copiedFile?.absolutePath
                }
            }
        }
    )

    val photoPickerActivity = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = {
            it?.let {
                uri.value = it
                val copiedFile = copyFileToInternalStorage(context, it)
                filePath.value = copiedFile?.absolutePath
            }
        }
    )

    val REQUEST_CODE_CAMERA = 2137420

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                modifier = Modifier
                    .width(144.dp),
                onClick = {
                    photoPickerActivity.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                }
            ) {
                Icon(painterResource(R.drawable.add_photo_icon), contentDescription = "Add/Edit photo")
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = label, style = MaterialTheme.typography.labelMedium)
            }

            Spacer(modifier = Modifier.width(8.dp))

            Button(
                modifier = Modifier.width(144.dp),
                onClick = {
                    if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(context as Activity, arrayOf(Manifest.permission.CAMERA), REQUEST_CODE_CAMERA)
                    } else {
                        cameraPhotoUri.value = createImageUri(context)
                        cameraPhotoUri.value?.let {
                            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE).apply {
                                putExtra(MediaStore.EXTRA_OUTPUT, it)
                            }
                            cameraActivity.launch(intent)
                        }
                    }
                }
            ) {
                Icon(painterResource(R.drawable.take_photo_icon), contentDescription = "Take photo")
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Take photo", style = MaterialTheme.typography.labelMedium)
            }
        }
    }

    return filePath.value
}


private fun copyFileToInternalStorage(context: Context, uri: Uri): File? {
    val fileName = getFileNameFromUri(context, uri) ?: "copied_image"
    val outputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE)
    val inputStream = context.contentResolver.openInputStream(uri) ?: return null

    inputStream.use { input ->
        outputStream.use { output ->
            input.copyTo(output)
        }
    }

    return File(context.filesDir, fileName)
}


private fun getFileNameFromUri(context: Context, uri: Uri): String? {
    if (uri.scheme == "content") {
        context.contentResolver.query(uri, null, null, null, null)?.use { cursor ->
            if (cursor.moveToFirst()) {
                val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                if (nameIndex != -1) {
                    return cursor.getString(nameIndex)
                }
            }
        }
    }
    return uri.path?.lastIndexOf('/')?.let { uri.path?.substring(it + 1) }
}

private fun createImageUri(context: Context): Uri? {
    val contentResolver = context.contentResolver
    val imageCollection = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
    val imageDetails = ContentValues().apply {
        put(MediaStore.Images.Media.DISPLAY_NAME, "camera_photo" + System.currentTimeMillis().toString() + ".jpg")
        put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
    }
    return contentResolver.insert(imageCollection, imageDetails)
}