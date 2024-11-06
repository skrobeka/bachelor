package com.example.bachelorv1.ui.book_details

import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
        /*Top part of the app */
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
                Spacer(modifier = Modifier.height(32.dp))
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

        /*Middle part of the app */




    }
}



@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    BachelorV1Theme {
        BookDetailsScreen()
    }
}
