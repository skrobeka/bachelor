package com.example.bachelorv1.ui.book_details

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
fun BookDetailsScreen(
    //navController: NavController,
    //book: Book?
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
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier
                    .size(width = 128.dp, height = 192.dp)
                    .clip(MaterialTheme.shapes.small),
                contentScale = ContentScale.FillWidth,
                painter = painterResource(R.drawable.book_details_screen),
                contentDescription = null
            )

            Spacer(modifier = Modifier.width(32.dp))

            Column(
                modifier = Modifier
                    .height(192.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Bottom
            ) {
                Column {
                    Text(text = "Book title", style = MaterialTheme.typography.titleLarge)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Author name", style = MaterialTheme.typography.titleMedium)
                }

                Button(
                    modifier = Modifier
                        .fillMaxWidth(),
                    onClick = {/*Add to favorites*/},
                ) {
                    Icon(Icons.Default.FavoriteBorder, contentDescription = "Add to favorites")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Add to favorites", style = MaterialTheme.typography.labelMedium)
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        /*Middle part of the screen*/
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.LightGray, MaterialTheme.shapes.small)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(painterResource(R.drawable.book_icon), contentDescription = "Book icon")
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(text = "Title", style = MaterialTheme.typography.bodyMedium)
                    Text(text = "Book title", style = MaterialTheme.typography.titleMedium)
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(painterResource(R.drawable.author_icon), contentDescription = "Author icon")
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(text = "Author", style = MaterialTheme.typography.bodyMedium)
                    Text(text = "Author name", style = MaterialTheme.typography.titleMedium)
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        /*Bottom part of the screen*/
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                modifier = Modifier
                    .weight(1f),
                onClick = {/*Add a photo*/ }
            ) {
                Icon(painterResource(R.drawable.add_a_photo_icon), contentDescription = "Add a photo")
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Add a photo", style = MaterialTheme.typography.labelMedium)
            }

            Spacer(modifier = Modifier.width(32.dp))

            Button(
                modifier = Modifier
                    .weight(1f),
                onClick = {/*Delete book*/ }
            ) {
                Icon(Icons.Default.Delete, contentDescription = "Delete book")
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Delete book", style = MaterialTheme.typography.labelMedium)
            }
        }
    }
}




@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    BachelorV1Theme {
        BookDetailsScreen()
    }
}
