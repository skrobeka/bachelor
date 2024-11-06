package com.example.bachelorv1.ui.add_book

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bachelorv1.R
import com.example.bachelorv1.ui.theme.BachelorV1Theme

@Composable
fun AddBookScreen(
    //navController: NavController
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 32.dp)
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

        Spacer(modifier = Modifier.padding(16.dp))

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

        Spacer(modifier = Modifier.padding(16.dp))

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
                            .background(Color.LightGray, MaterialTheme.shapes.small)
                            .fillMaxWidth(),
                        value = "",
                        onValueChange = {},
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
                            .background(Color.LightGray, MaterialTheme.shapes.small)
                            .fillMaxWidth(),
                        value = "",
                        onValueChange = {},
                        label = { Text("Author's name") }
                    )
                }
            }
            
            Spacer(modifier = Modifier.padding(16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    modifier = Modifier
                        .fillMaxWidth(),
                    onClick = {/*Save book to your library*/ }
                ) {
                    Icon(painterResource(R.drawable.save_icon), contentDescription = "Save book to your library")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Save book to your library", style = MaterialTheme.typography.labelMedium)
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    BachelorV1Theme {
        AddBookScreen()
    }
}
