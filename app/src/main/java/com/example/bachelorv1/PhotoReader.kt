package com.example.bachelorv1

import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.asImageBitmap
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions

fun readTextFromPicture(filePath: String, callback: (String) -> Unit) {
    val imageBitmap : ImageBitmap = BitmapFactory.decodeFile(filePath).asImageBitmap()
    val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)

    val image = InputImage.fromBitmap(imageBitmap.asAndroidBitmap(), 0)
    val result = recognizer.process(image)

    result.addOnSuccessListener { visionText ->
        callback(visionText.text)
    }
    result.addOnFailureListener { e ->
        callback("Error reading text from image")
    }

    return
}